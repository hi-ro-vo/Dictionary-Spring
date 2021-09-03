package ru.test.dictionaries.service;

import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.viewentities.Word;

import java.util.List;

public interface DictionariesService {

    void addWord(Word word);

    void removeWord(Word word);

    void editWord(Word oldWord, Word newWord);

    void saveWord(Word word);

    Word getWord(DictionaryType type, String foreignWord);

    List<Word> getDictionary(DictionaryType type);


}
