package ru.test.dictionaries.dao;

import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.ForeignWord;
import ru.test.dictionaries.entity.TranslatedWord;

import java.util.List;


public interface DictionaryDao {
    List<ForeignWord> getDictionary(DictionaryType type);

    void addForeignWord(ForeignWord entity);

    void editEntry(ForeignWord entity);

    ForeignWord getForeignWord(DictionaryType type, String foreignWord);

    void removeEntity(DictionaryType type, String key, String value);

    void addTranslatedWord(TranslatedWord translatedWord);

    TranslatedWord getTranslatedWord(ForeignWord foreignWord, String translation);

    List<ForeignWord> findByForeignInAllPlaces(String foreignWord);

    List<TranslatedWord> findByTranslationInAllPlaces(String translation);

    List<TranslatedWord> findByTranslationInDictionary(DictionaryType type, String translation);

    List<ForeignWord> findByForeignInDictionary(DictionaryType type, String foreignWord);
}
