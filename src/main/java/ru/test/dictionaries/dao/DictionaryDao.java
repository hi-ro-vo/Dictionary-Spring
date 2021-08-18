package ru.test.dictionaries.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

@Transactional
public interface DictionaryDao {
    Dictionary getDictionary(DictionaryType type);

    Entry getEntry(DictionaryType type, String key);

    void saveEntry(Entry entity);

    void saveDictionary(Dictionary dictionary);

    Entry getEntry(DictionaryType type, String key, String value);

    void removeEntity(DictionaryType type, String key, String value);
}
