package ru.test.dictionaries.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.dictionary.DictionaryFactory;
import ru.test.dictionaries.entity.DictionaryEntity;
import ru.test.dictionaries.entity.EntryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("JpaDictionaryDao")
@Transactional
public class JpaDictionaryDao implements DictionaryDao{

    private DictionaryFactory dictionaryFactory;

    @Autowired
    JpaDictionaryDao(DictionaryFactory dictionaryFactory){
        this.dictionaryFactory = dictionaryFactory;
    };

    @PersistenceContext
    private EntityManager em;


    @Override
    public AbstractDictionary getDictionary(DictionaryType type) {
        DictionaryEntity entity = em.find(DictionaryEntity.class, 1L);
        AbstractDictionary dictionary = dictionaryFactory.getDictionary(type);
        dictionary.setDictionaryEntity(entity);
        return dictionary;
    }

    @Override
    public EntryEntity getEntryFromDictionaryByKey(String key, DictionaryType type) {
        return null;
    }

    @Override
    public void saveEntry(EntryEntity entity) {

    }

    @Override
    public void saveDictionary(AbstractDictionary dictionary) {

    }
}
