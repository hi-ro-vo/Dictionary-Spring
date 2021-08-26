package ru.test.dictionaries;

import org.slf4j.LoggerFactory;

public enum DictionaryType {
    LATINIC_DICTIONARY {
        @Override
        public boolean isRuleFulfilled(String s) {
            LoggerFactory.getLogger(DictionaryType.class).debug("Is {} fulfill rule : {}", s, s.matches("^[a-zA-Z]{4}$"));
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
