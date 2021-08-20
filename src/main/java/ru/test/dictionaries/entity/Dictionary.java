package ru.test.dictionaries.entity;

import ru.test.dictionaries.DictionaryType;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "dictionaryentity")
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    DictionaryType dictionaryType;


    @OneToMany(mappedBy = "dictionary", orphanRemoval=true, fetch = FetchType.EAGER)
    Set<Entry> entries;

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

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }
}
