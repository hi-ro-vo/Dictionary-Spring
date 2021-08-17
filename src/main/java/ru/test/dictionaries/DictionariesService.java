package ru.test.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.dictionary.DictionaryFactory;

@Service
public class DictionariesService {

    @Autowired
    private DictionaryFactory dictionaryFactory;

    @Autowired
    private DictionaryDao dictionaryDao;

    public void addOrUpdateEntry(){

    }
}
