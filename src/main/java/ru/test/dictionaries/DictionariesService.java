package ru.test.dictionaries;

import ru.test.dictionaries.viewentities.Word;

import java.util.Set;

public interface DictionariesService {

    void addWord(Word word);

    void removeWord(Word word);

    void editWord(Word oldWord, Word newWord);

    void saveWord(Word word);

    Word getWord(DictionaryType type, String key, String value);

    Set<Word> getDictionary(DictionaryType type);


}
