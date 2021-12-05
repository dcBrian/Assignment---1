package com.project.homework.utils;

/**
 * Utility Class for Files
 */
public class Properties {
    private Properties() {
    }

    public static final String USER_ROOT_PATH = System.getProperty("user.dir") + Utils.SEPARATOR;

    public static final String RESOURCE_FOLDER_NAME = "pages";
    public static final String RESOURCES_PATH = USER_ROOT_PATH + RESOURCE_FOLDER_NAME + Utils.SEPARATOR;

    public static final String METADATA_FILENAME = "metadata.txt";
}
