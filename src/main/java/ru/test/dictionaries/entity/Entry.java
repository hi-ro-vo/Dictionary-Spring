package ru.test.dictionaries.entity;

import ru.test.dictionaries.viewentities.Word;

import javax.persistence.*;

@Entity(name = "entryentity")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String keyValue;

    String value;

    @ManyToOne
    @JoinColumn(name = "dictionary_id", nullable = false)
    Dictionary dictionary;

    public Entry() {
    }

    public Entry(Word word, Dictionary dictionary) {
        this.keyValue = word.getKey();
        this.value = word.getValue();
        this.dictionary = dictionary;
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
