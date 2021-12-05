package com.project.homework.parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.homework.handler.IOHandler;
import com.project.homework.model.Metadata;
import com.project.homework.model.Option;
import com.project.homework.printer.Printer;
import com.project.homework.printer.Terminal;
import com.project.homework.utils.Messages;
import com.project.homework.utils.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupParser {
    private static final Printer printer = Terminal.INSTANCE;

    private final String url;
    private final String path;

    private Map<Option, Integer> options;
    private boolean optionMetadata;

    private Metadata metadata;
    private Document document;

    public JsoupParser(String url, String path, Map<Option, Integer> options) {
        this.url = url;
        this.path = path;

        this.metadata = new Metadata(url);

        this.options = options;
        this.optionMetadata = this.options.containsKey(Option.METADATA);
    }

    /**
     * Executes the request as a GET, and parse the result into a JSOUP Document.
     *
     * @throws IOException
     */
    public void fetch() throws IOException {
        printer.printPrettyUp("Fetching: %s", this.url);

        Connection connection = Jsoup.connect(this.url);

        // Header Options
        connection.userAgent("Mozilla");
        connection.referrer("http://google.com");

        // Default Connection timeout is 3 seconds
        this.document = connection.get();

        printer.printOnlyIf(this.optionMetadata, "Last fetch: %s", Utils.formatLocalDate(this.metadata.getLastFetch()));
    }

    /**
     * Handles all operations to be done on a JSOUP Document.
     */
    public Metadata recordElements() {
        Elements links = this.document.select("a[href]");
        Elements assets = this.document.select("[src]");
        Elements imports = this.document.select("link[href]");
        List<Element> images = assets.stream().filter(e -> e.tagName().equals("img")).collect(Collectors.toList());

        // Links
        this.metadata.setLinks(links.size());
        printer.printOnlyIf(this.optionMetadata, "Links: %d", links.size());

        // Assets
        this.metadata.setAssets(assets.size());
        this.metadata.setImages(images.size());
        printer.printOnlyIf(this.optionMetadata, "Assets: %d (%d Images)", metadata.getAssets(), metadata.getImages());

        // Imports (<link /> tag)
        this.metadata.setImports(imports.size());
        printer.printOnlyIf(this.optionMetadata, "Imports: %d", imports.size());

        // Archive Elements
        if (this.options.containsKey(Option.ASSETS) && this.metadata.getAssets() != 0) {
            int success = archiveElements(assets, "src");
            printer.print(Messages.SUCCESS_ASSETS, success, this.metadata.getAssets());
        }

        if (this.options.containsKey(Option.IMPORTS) && this.metadata.getImports() != 0) {
            int success = archiveElements(imports, "href");
            printer.print(Messages.SUCCESS_IMPORTS, success, this.metadata.getImports());
        }

        return this.metadata;
    }

    /**
     * For each Element of a given list, archives its resource
     * and modify its attribute to point to the local copy.
     * Returns the number of successful operations.
     *
     * @param elements
     * @param attribute
     * @return int
     */
    private int archiveElements(List<Element> elements, String attribute) {
        int success = 0;

        for (Element element : elements) {

            String abs = element.attr("abs:" + attribute); /* URL to fetch resource from */
            String src = Utils.replaceScheme(element.attr(attribute), "");
            String assetPath = this.path + Utils.replaceInvalidFileChars(src, "_"); /* Builds the name of file */

            // If base64 Image :: Already accessible offline
            if ("".equals(abs)) {
                continue;
            }

            // Update element attribute if success
            if (IOHandler.downloadResourceFromURL(abs, assetPath)) {
                element.attr(attribute, assetPath);
                success++;

                if ("src".equals(attribute)) {
                    element.removeAttr("srcset");
                }
            }
        }
        return success;
    }

    public Document getDocument() {
        return this.document;
    }
}
