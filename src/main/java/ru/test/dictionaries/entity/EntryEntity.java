package ru.test.dictionaries.entity;

import javax.persistence.*;

@Entity
//@Table(name="Entry")
public class EntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String keyValue;

    String value;

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
        this.keyValue = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public DictionaryEntity getDictionaryEntity() {
//        return dictionaryEntity;
//    }
//
//    public void setDictionaryEntity(DictionaryEntity dictionaryEntity) {
//        this.dictionaryEntity = dictionaryEntity;
//    }
}
