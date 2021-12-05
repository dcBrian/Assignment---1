package com.project.homework.model;

/**
 * Enum describing all the available CLI options.
 */
public enum Option {
    METADATA("--metadata"), /* Records and prints metadata */
    ASSETS("--assets"), /* Saves Assets */
    IMPORTS("--imports"), /* Saves Imports */

    COPY("--copy"), /* ASSETS + IMPORTS */
    ALL("--all"), /* METADATA + ASSETS + IMPORTS */

    UNKNOWN("");

    private final String input;

    Option(String input) {
        this.input = input;
    }

    /**
     * Returns the ENUM value that has the given String as input property.
     * Returns Option.UNKNOWN if no match.
     * 
     * @param input
     */
    public static Option valueOfString(String input) {
        for (Option e : values()) {
            if (e.input.equals(input) && !e.equals(UNKNOWN)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
