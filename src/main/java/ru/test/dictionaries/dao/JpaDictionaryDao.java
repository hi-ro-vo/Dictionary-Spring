package ru.test.dictionaries.dao;


import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//@Repository("JpaDictionaryDao")
@Transactional
public class JpaDictionaryDao implements DictionaryDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public Dictionary getDictionary(DictionaryType type) {
        //Dictionary entity = em.find(Dictionary.class, 1L);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Dictionary> dictionaryCriteriaQuery = criteriaBuilder.createQuery(Dictionary.class);
        Root<Dictionary> dictionaryRoot = dictionaryCriteriaQuery.from(Dictionary.class);
        dictionaryCriteriaQuery.where(criteriaBuilder.equal(dictionaryRoot.get("dictionaryType"), type));

        Dictionary entity = em.createQuery(dictionaryCriteriaQuery).getSingleResult();

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

    @Override
    public void removeEntity(DictionaryType type, String key, String value) {

    }
}
