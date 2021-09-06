package ru.test.dictionaries.service;

import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.viewentities.Word;

import java.util.List;

public interface DictionariesService {

    void addWord(Word word);

    void removeTranslation(DictionaryType type, String foreignWord, String translation);

    void editWord(Word oldWord, Word newWord);

    void saveWord(Word word);

    Word getWord(DictionaryType type, String foreignWord);

    List<Word> getDictionary(DictionaryType type);

    List<Word> findByForeignInAllPlaces(String foreignWord);

    List<Word> findByTranslationInAllPlaces(String translation);
}
