package com.project.homework.utils;

public class Messages {
    private Messages() {
    }

    public static final String APP_START = "<Application started>";
    public static final String APP_END = "<Application finished>";

    public static final String NO_URLS_PROVIDED = "Error: No URLs were provided";

    public static final String NOT_VALID_URL = "Error: Argument '%s' is not a valid URL";
    public static final String NOT_VALID_OPTION = "Error: Argument %s is not a valid option";

    public static final String SUCCESS_HTML = "HTML file archived successfully";
    public static final String SUCCESS_METADTA = "Metadata file archived successfully";
    public static final String SUCCESS_ASSETS = "Assets archived successfully: %d/%d (Base64 images not archived)";
    public static final String SUCCESS_IMPORTS = "Imports archived successfully: %d/%d";

    public static final String FLOW_IO = "Error - Could not get any HTTP response: \n%s";
    public static final String FLOW_FILE_NOT_FOUND = "Error - File could not be archived: \n%s";
    public static final String FLOW_URL_NOT_VALID = "Error - Argument %s is not a valid URL: \n%s";
    public static final String FLOW_STATUS_NOT_VALID = "Error - HTTP request for the URL:%s resulted in a not OK response: \n%s";

    public static final String RESOURCE_URL_NOT_VALID = "Error: Resource's URL is not valid: %s \n%s";
    public static final String RESOURCE_IO = "Error: Can't download Resource: %s \n%s";
    public static final String BASE_64_IO = "Error: Can't save base64 image: \n%s";
}
