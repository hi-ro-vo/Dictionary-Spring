package ru.test.dictionaries.entity;

import ru.test.dictionaries.DictionaryType;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;

@Entity(name = "entryentity")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //@Pattern(regexp = "^[0-9a-zA-Z]{1,5}$", message = "key")
    String keyValue;

    String value;
    @ManyToOne
    @JoinColumn(name = "dictionary_id", nullable = false)
    Dictionary dictionary;

    @Transient
    private DictionaryType type;

    public DictionaryType getType() {
        return type;
    }

    public void setType(DictionaryType type) {
        this.type = type;
    }


    @AssertTrue(message = "key didn't suite dictionary rule")
    public boolean isValid() {
        if (type == null) {
            return true;
        }
        return type.isRuleFulfilled(keyValue);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String key) {
        if (dictionary != null) {
            if (dictionary.dictionaryType.isRuleFulfilled(key)) {
                this.keyValue = key;
            } else {
                //TODO ошибочка туть
            }
        } else {
            this.keyValue = key;
        }

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
