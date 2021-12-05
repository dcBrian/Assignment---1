package com.project.homework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.project.homework.handler.IOHandler;
import com.project.homework.model.Metadata;
import com.project.homework.model.Option;
import com.project.homework.parser.JsoupParser;
import com.project.homework.printer.Printer;
import com.project.homework.printer.Terminal;
import com.project.homework.utils.Messages;
import com.project.homework.utils.Properties;
import com.project.homework.utils.Utils;

import org.jsoup.HttpStatusException;

public class CommandLine {
    private static final Printer printer = Terminal.INSTANCE;
    private Map<Option, Integer> options = new EnumMap<>(Option.class);
    private List<String> probablyOptions = new ArrayList<>();
    private List<String> probablyUrls = new ArrayList<>();

    /**
     * Constructor - Initialize the Application
     */
    public CommandLine() {
        // Creates the ROOT directory for resources
        IOHandler.createDirectoriesIfMissing(Properties.RESOURCES_PATH);
    }

    /**
     * Main method
     *
     * @param args
     */
    public void run(String... args) {

        printer.printPretty(Messages.APP_START);

        // Sort arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                this.probablyOptions.add(args[i]);
            } else {
                this.probablyUrls.add(args[i]);
            }
        }

        // Nothing to process :: Stop application
        if (this.probablyUrls.isEmpty()) {
            printer.printPretty(Messages.NO_URLS_PROVIDED);
            printer.printPretty(Messages.APP_END);
            return;
        }

        // Handles Options
        this.probablyOptions.stream().forEach(this::processOption);

        // Handles URLs
        this.probablyUrls.stream().forEach(this::processURL);

        printer.printPretty(Messages.APP_END);
    }

    /**
     * Handles operations related to the different options.
     * 
     * @param option
     */
    private void processOption(String option) {
        Option value = Option.valueOfString(option);

        switch (value) {
            case METADATA:
                this.options.put(Option.METADATA, 0);
                break;

            case ASSETS:
                this.options.put(Option.ASSETS, 0);
                break;

            case IMPORTS:
                this.options.put(Option.IMPORTS, 0);
                break;

            case COPY:
                this.options.put(Option.ASSETS, 0);
                this.options.put(Option.IMPORTS, 0);
                break;

            case ALL:
                this.options.put(Option.METADATA, 0);
                this.options.put(Option.ASSETS, 0);
                this.options.put(Option.IMPORTS, 0);
                break;

            default:
                printer.printPretty(Messages.NOT_VALID_OPTION, option);
                break;
        }
    }

    /**
     * Handles operations for a given URL.
     *
     * @param url
     */
    private void processURL(String url) {
        // Verify URL before processing :: Exception should not be used to control flow
        if (!Utils.isValidURL(url)) {
            printer.printPretty(Messages.NOT_VALID_URL, url);
            return;
        }

        // Create URL's Resource folder name from domain
        String domain = Utils.replaceInvalidFileChars(Utils.getFullDomain(url), "_");
        String directoryPath = Properties.RESOURCES_PATH + domain + Utils.SEPARATOR;

        String htmlFilePath = directoryPath + domain + ".html";
        String metadataFilePath = directoryPath + Properties.METADATA_FILENAME;

        try {
            // Fetch && parse HTML
            JsoupParser documentParser = new JsoupParser(url, directoryPath, this.options);
            documentParser.fetch();

            // Create URL's Resource folder
            IOHandler.createDirectoriesIfMissing(directoryPath);

            // Where the magic happens
            Metadata metadata = documentParser.recordElements();

            // Save modified HTML
            IOHandler.writeStringToFile(documentParser.getDocument().outerHtml(), htmlFilePath);
            printer.print(Messages.SUCCESS_HTML);

            // Archive Metadata conditionally
            if (this.options.containsKey(Option.METADATA)) {
                IOHandler.writeStringToFile(metadata.toString(), metadataFilePath);
                printer.print(Messages.SUCCESS_METADTA);
            }

        } catch (HttpStatusException e) {
            // Catch abnormal status codes (e.g. 404)
            printer.printPretty(Messages.FLOW_STATUS_NOT_VALID, url, e.toString());

        } catch (MalformedURLException e1) {
            printer.printPretty(Messages.FLOW_URL_NOT_VALID, url, e1.toString());

        } catch (FileNotFoundException e2) {
            printer.printPretty(Messages.FLOW_FILE_NOT_FOUND, e2.toString());

        } catch (IOException e3) {
            // Unexpected IO exception :: Most likely
            // - No response from HTTP Request
            // - Request timeout
            // - SSL certificates are being blocked
            printer.printPretty(Messages.FLOW_IO, e3.toString());
        }
    }

}
