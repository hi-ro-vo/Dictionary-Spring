package ru.test.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.dictionaries.dao.DictionaryDao;

import ru.test.dictionaries.entity.Entry;

import java.util.List;
import java.util.Map;

public interface DictionariesService {

    void addEntry(DictionaryType type, String key, String value);
    void removeEntry(DictionaryType type, String key, String value);
    void editEntry(DictionaryType type, String key, String value, String newKey, String newValue);

    List<Entry> getDictionary(DictionaryType type);


}
