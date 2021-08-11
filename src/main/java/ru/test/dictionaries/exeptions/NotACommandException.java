package ru.test.dictionaries.exeptions;

public class NotACommandException extends Exception {
    public NotACommandException(String message) {
        super(message);
    }
}
