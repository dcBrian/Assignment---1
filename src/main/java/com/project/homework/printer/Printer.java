package com.project.homework.printer;

public interface Printer {

    public void print(String msg, Object... args);

    public void printOnlyIf(boolean condition, String msg, Object... args);

    public void printPrettyUp(String msg, Object... args);

    public void printPretty(String msg, Object... args);

}
