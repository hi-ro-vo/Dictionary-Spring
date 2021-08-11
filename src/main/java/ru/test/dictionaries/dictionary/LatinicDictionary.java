package ru.test.dictionaries.dictionary;

import org.springframework.stereotype.Component;

@Component("Latinic")
public class LatinicDictionary extends AbstractDictionary {

    @Override
    public boolean isRuleFulfilled(String s) {
        return s.matches("^[a-zA-Z]{4}$");
    }
}
