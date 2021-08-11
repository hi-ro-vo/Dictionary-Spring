package ru.test.dictionaries.dictionary;


import org.springframework.stereotype.Component;

@Component
public class NumericDictionary extends AbstractDictionary {

    @Override
    public boolean isRuleFulfilled(String s) {
        return s.matches("^[0-9]{5}$");
    }
}
