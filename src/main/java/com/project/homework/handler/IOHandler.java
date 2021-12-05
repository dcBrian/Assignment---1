package com.project.homework.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.project.homework.printer.Printer;
import com.project.homework.printer.Terminal;
import com.project.homework.utils.Messages;

public class IOHandler {
    private static final Printer printer = Terminal.INSTANCE;

    private IOHandler() {
    }

    /**
     * Handles the creation of one or multiple directories on a given path.
     *
     * @param path
     */
    public static void createDirectoriesIfMissing(String path) {
        // TODO :: handle case where already existing file has the same name
        File f = new File(path);

        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * Opens a connection to the given URL and returns a byte[] for reading from
     * that connection.
     *
     * @param url
     * @return byte[]
     * @throws MalformedURLException,IOException
     */
    public static byte[] urlToByteArray(String url) throws IOException {
        final byte[] bytes;
        try (InputStream is = new BufferedInputStream(new URL(url).openStream());) {
            bytes = is.readAllBytes();
        }
        return bytes;
    }

    /**
     * Writes a String into the file with the specified name.
     *
     * @param str
     * @param fileName
     * @throws FileNotFoundException
     */
    public static void writeStringToFile(String str, String fileName) throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(str);
        }
    }

    /**
     * Writes a byte[] into the file with the specified name.
     *
     * @param bytes
     * @param fileName
     * @throws IOException
     */
    public static void writeBytesToFile(byte[] bytes, String fileName) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(bytes);
        }
    }

    /**
     * Handles the download of a resource from a given URL. The resource is written
     * into the file with the specified name. Errors are handled locally because
     * method is used for Assets & Imports which are not mandatory in order
     * to read the HTML.
     * 
     * @param url
     * @param fileName
     * @return boolean
     */
    public static boolean downloadResourceFromURL(String url, String fileName) {

        try {
            byte[] bytes = urlToByteArray(url);
            writeBytesToFile(bytes, fileName);
            return true;

        } catch (MalformedURLException e1) {
            printer.printPretty(Messages.RESOURCE_URL_NOT_VALID, url, e1.toString());

        } catch (IOException e2) {
            printer.printPretty(Messages.RESOURCE_IO, url, e2.toString());
        }

        return false;
    }

    /**
     * Method not used
     * No need to archive Base64 images as they are already included in HTML.
     *
     * Writes a base64 into the file with the specified name. Errors are
     * handled locally because Images are not mandatory to read the HTML.
     *
     * @param bytes
     * @param fileName
     * @throws IOException
     * @return boolean
     */
    public static boolean writeBase64Image(String base64, String fileName) {
        String[] strings = base64.split(",");
        String extension;

        // Retrieve Base64's extension
        switch (strings[0]) {
            case "data:image/jpeg;base64":
                extension = ".jpeg";
                break;
            case "data:image/png;base64":
                extension = ".png";
                break;
            case "data:image/gif;base64":
                extension = ".gif";
                break;
            default:
                extension = "jpg";
                break;
        }

        try {
            byte[] data = Base64.getDecoder().decode(strings[1].getBytes(StandardCharsets.UTF_8));
            writeBytesToFile(data, fileName + extension);
            return true;

        } catch (IOException e) {
            printer.printPretty(Messages.BASE_64_IO, e.toString());
        }
        return false;
    }
}
