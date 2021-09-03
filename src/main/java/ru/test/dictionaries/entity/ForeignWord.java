package ru.test.dictionaries.entity;

import ru.test.dictionaries.DictionaryType;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "foreign_word")
public class ForeignWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @OneToMany(mappedBy = "foreignWord", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TranslatedWord> translatedWords;

    @Enumerated(EnumType.STRING)
    private DictionaryType dictionaryType;

    public ForeignWord() {
    }

    public ForeignWord(DictionaryType dictionaryType, String key) {
        this.dictionaryType = dictionaryType;
        this.word = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TranslatedWord> getTranslatedWords() {
        return translatedWords;
    }

    public void setTranslatedWords(Set<TranslatedWord> translatedWords) {
        this.translatedWords = translatedWords;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }
}
