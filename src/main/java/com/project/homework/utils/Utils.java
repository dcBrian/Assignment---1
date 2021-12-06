package com.project.homework.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String SEPARATOR = File.separator;
    public static final String LINE = System.getProperty("line.separator");

    // URL Validation Regex (from OWASP)
    private static final String VALIDATION_REGEX = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
            "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" + "([).!';/?:,][[:blank:]])?$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);

    // Breaking-down a well-formed URI Regex
    private static final String URI_GROUP_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
    private static final Pattern URI_GROUP__PATTERN = Pattern.compile(URI_GROUP_REGEX);

    // URL Scheme Regex
    public static final String SCHEME_REGEX = "^(https?://)";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MMM yyyy HH:mm:ss");

    private Utils() {
    }

    /**
     * Checks if an URL is valid.
     *
     * @param url
     * @return boolean
     */
    public static boolean isValidURL(String url) {
        if (url == null) {
            return false;
        }

        Matcher matcher = VALIDATION_PATTERN.matcher(url);
        return matcher.matches();
    }

    /**
     * Generates the name of the page folder.
     *
     * @param url
     * @return String
     */
    public static String getFullDomain(String url) {
        Matcher m = URI_GROUP__PATTERN.matcher(url);
        String result;

        // Easier to modify URL's output with matcher
        if (m.find()) {
            // returns URL's domain only
            // return m.group(4)

            // returns URL's full domain
            result = m.group(4) + m.group(5);
        } else {

            // Backup :: delete URL's scheme
            result = replaceScheme(url, "");
        }

        // Remove last "/"
        if (result.endsWith("/")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * Formats the given LocalDateTime to be printed.
     *
     * @param date
     * @return String
     */
    public static String formatLocalDate(LocalDateTime date) {
        return date.format(FORMATTER);
    }

    /**
     * Replaces URL scheme in a given string.
     * 
     * @param fileName
     * @param replacement
     * @return String
     */
    public static String replaceScheme(String fileName, String replacement) {
        return fileName.replaceAll(SCHEME_REGEX, replacement);
    }

    /**
     * Replaces invalid characters in a file name and removes leading "_".
     * 
     * @param fileName
     * @param replacement
     * @return String
     */
    public static String replaceInvalidFileChars(String fileName, String replacement) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", replacement).replaceAll("^_", "");
    }

    /**
     * Retrieve base64 Image extension from source attribute.
     * 
     * @param source
     * @return String
     */
    public static String getBase64Extension(String source) {
        String[] strings = source.split(",");
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
        return extension;
    }
}
