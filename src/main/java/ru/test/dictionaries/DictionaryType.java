package ru.test.dictionaries;

public enum DictionaryType {
    LATINIC_DICTIONARY {
        @Override
        public boolean isRuleFulfilled(String s) {
            return s.matches("^[a-zA-Z]{4}$");
        }
    },
    NUMERIC_DICTIONARY {
        @Override
        public boolean isRuleFulfilled(String s) {
            return s.matches("^[0-9]{5}$");
        }
    };

    public abstract boolean isRuleFulfilled(String s);
}
