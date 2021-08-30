package ru.test.dictionaries.viewentities;

import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Entry;

import javax.validation.constraints.AssertTrue;

public class Word {


    private String key;

    private String value;

    private DictionaryType type;

    public Word() {
    }

    public Word(String key, String value, DictionaryType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public Word(Entry entry) {
        this.key = entry.getKeyValue();
        this.value = entry.getValue();
        this.type = entry.getDictionary().getDictionaryType();
    }

    @AssertTrue(message = "key didn't suite dictionary rule")
    public boolean isValid() {
        if (type == null || key == null) {
            return true;
        }
        return type.isRuleFulfilled(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DictionaryType getType() {
        return type;
    }

    public void setType(DictionaryType type) {
        this.type = type;
    }
}
