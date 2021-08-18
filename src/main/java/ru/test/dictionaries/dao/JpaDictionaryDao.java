package ru.test.dictionaries.dao;


import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Repository("JpaDictionaryDao")
@Transactional
public class JpaDictionaryDao implements DictionaryDao{




    @PersistenceContext
    private EntityManager em;


    @Override
    public Dictionary getDictionary(DictionaryType type) {
        Dictionary entity = em.find(Dictionary.class, 1L);

        return entity;
    }

    @Override
    public Entry getEntry(DictionaryType type, String key) {
        return null;
    }

    @Override
    public void saveEntry(Entry entity) {

    }

    @Override
    public void saveDictionary(Dictionary dictionary) {

    }

    @Override
    public Entry getEntry(DictionaryType type, String key, String value) {
        return null;
    }
}
