package ru.test.dictionaries;

import ru.test.dictionaries.entity.Entry;

import java.util.Set;

public interface DictionariesService {

    void addEntry(DictionaryType type, String key, String value);

    void removeEntry(DictionaryType type, String key, String value);

    void editEntry(DictionaryType type, String key, String value, String newKey, String newValue);

    Set<Entry> getDictionary(DictionaryType type);


}
