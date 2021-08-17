package ru.test.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.dictionary.DictionaryFactory;

import java.util.List;
import java.util.Map;

public interface DictionariesService {

    void addEntry(DictionaryType type, String key, String value);
    void updateEntry(DictionaryType type, String key, String value);
    void removeEntry(DictionaryType type, String key, String value);

    Map<String, List<String>> getDictionary(DictionaryType type);


}
