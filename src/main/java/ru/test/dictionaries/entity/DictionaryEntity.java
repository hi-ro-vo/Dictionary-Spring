package ru.test.dictionaries.entity;

import ru.test.dictionaries.DictionaryType;

import javax.persistence.*;
import java.util.List;

@Entity
public class DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    DictionaryType dictionaryType;

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setDD(String d) {
        this.d = d;
    }

    String d;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dictionary_entity", joinColumns = {@JoinColumn(name = "dictionary_id")},
            inverseJoinColumns = {@JoinColumn(name = "entry_id")})
    List<EntryEntity> entries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    public List<EntryEntity> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryEntity> entries) {
        this.entries = entries;
    }
}
