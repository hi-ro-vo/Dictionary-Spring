package ru.test.dictionaries;

public enum DictionaryType {
    LATINIC_DICTIONARY("^[a-zA-Z]{4}$"),
    NUMERIC_DICTIONARY("^[0-9]{5}$");

    private final String regex;

    DictionaryType(String regex) {
        this.regex = regex;
    }

    public boolean isRuleFulfilled(String key) {
        return key.matches(regex);
    }
}
