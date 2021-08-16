package ru.test.dictionaries.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.entity.EntryEntity;

@Transactional
public interface DictionaryDao {
    AbstractDictionary getDictionary(DictionaryType type);

    EntryEntity getEntryFromDictionaryByKey(String key, DictionaryType type);

    void saveEntry(EntryEntity entity);

    void saveDictionary(AbstractDictionary dictionary);
}
