package ru.test.dictionaries;

import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

public interface DictionariesService {

    void addEntry(DictionaryType type, String key, String value);

    void removeEntry(DictionaryType type, String key, String value);

    void editEntry(DictionaryType type, String key, String value, String newKey, String newValue);

    void saveEntry(Entry entity);

    Entry getEntry(DictionaryType type, String key, String value);

    Dictionary getDictionary(DictionaryType type);


}
