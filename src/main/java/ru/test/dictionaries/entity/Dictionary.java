package ru.test.dictionaries.entity;

import ru.test.dictionaries.DictionaryType;

import javax.persistence.*;
import java.util.List;

@Entity(name = "dictionaryentity")
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    DictionaryType dictionaryType;

    String d;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dictionary_entity", joinColumns = {@JoinColumn(name = "dictionary_id")},
            inverseJoinColumns = {@JoinColumn(name = "entry_id")})
    List<Entry> entries;

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

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
