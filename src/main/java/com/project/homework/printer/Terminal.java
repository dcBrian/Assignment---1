package com.project.homework.printer;

/**
 * Singleton Utility Class to print output to terminal
 * Best way to implement Singleton is ENUMS
 */
public enum Terminal implements Printer {
    INSTANCE;

    private boolean isLastLineEmpty = false;

    /**
     * Utility method for printing output to the terminal.
     *
     * @param msg
     * @param args
     */
    @Override
    public void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        this.isLastLineEmpty = false;
    }

    /**
     * Utility method for printing output to the terminal in a cleaner way.
     * 
     * @param msg
     * @param args
     */
    @Override
    public void printPrettyUp(String msg, Object... args) {
        String str = (this.isLastLineEmpty ? "" : "\n") + msg;
        print(str, args);
        this.isLastLineEmpty = false;
    }

    /**
     * Utility method for printing output to the terminal in a cleaner way.
     * 
     * @param msg
     * @param args
     */
    @Override
    public void printPretty(String msg, Object... args) {
        String str = (this.isLastLineEmpty ? "" : "\n") + msg + "\n";
        print(str, args);
        this.isLastLineEmpty = true;
    }

    /**
     * Utility method for printing output conditionally to the terminal.
     * 
     * @param condition
     * @param msg
     * @param args
     */
    @Override
    public void printOnlyIf(boolean condition, String msg, Object... args) {
        if (condition) {
            print(msg, args);
            this.isLastLineEmpty = false;
        }
    }
}