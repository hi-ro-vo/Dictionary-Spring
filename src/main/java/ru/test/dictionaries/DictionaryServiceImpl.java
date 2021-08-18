package ru.test.dictionaries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionariesService{

    public static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    DictionaryDao dictionaryDao;

    @Override
    public void addEntry(DictionaryType type, String key, String value) {
        if (type.isRuleFulfilled(key)){
            Entry entry = new Entry();
            entry.setKeyValue(key);
            entry.setValue(value);
            dictionaryDao.saveEntry(entry);
        } else {
            logger.warn("Can't add {} to {}.", key, type);
        }
    }

    @Override
    public void removeEntry(DictionaryType type, String key, String value) {

    }

    @Override
    public void editEntry(DictionaryType type, String key, String value, String newKey, String newValue) {

    }

    @Override
    public List<Entry> getDictionary(DictionaryType type) {
        Dictionary dictionary = dictionaryDao.getDictionary(type);
        return dictionary.getEntries();
    }
}
