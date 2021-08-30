package ru.test.dictionaries.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("JpaDictionaryDao")
@Transactional
public class JpaDictionaryDao implements DictionaryDao {

    private static final Logger logger = LoggerFactory.getLogger(JpaDictionaryDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public Dictionary getDictionary(DictionaryType type) {
        //Dictionary entity = em.find(Dictionary.class, 1L);
        logger.debug("Read dictionary: {}", type);

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
    public void addEntry(Entry entity) {
        logger.debug("Add new entry: {}-{}", entity.getKeyValue(), entity.getValue());
        em.persist(entity);
    }

    @Override
    public void editEntry(Entry entity) {
        logger.debug("Edit entry: {}-{}", entity.getKeyValue(), entity.getValue());
        em.merge(entity);
    }

    @Override
    public void saveDictionary(Dictionary dictionary) {

    }

    @Override
    public Entry getEntry(DictionaryType type, String key, String value) {

        //Long dictionaryId = getDictionaryId(type);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Entry> entryCriteriaQuery = criteriaBuilder.createQuery(Entry.class);

        Root<Entry> entryRoot = entryCriteriaQuery.from(Entry.class);
        entryCriteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(entryRoot.get("keyValue"), key),
                criteriaBuilder.equal(entryRoot.get("value"), value),
                criteriaBuilder.equal(entryRoot.get("dictionary"), getDictionary(type))));

        Entry entry = em.createQuery(entryCriteriaQuery).getSingleResult();

        logger.debug("Read entry: {}-{}", entry.getKeyValue(), entry.getValue());
        return entry;
    }

    @Override
    public void removeEntity(DictionaryType type, String key, String value) {
        logger.debug("Removed entry: {}-{}", key, value);
        Entry entry = getEntry(type, key, value);
        em.remove(entry);
    }

    private Long getDictionaryId(DictionaryType type) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Long> dictionaryCriteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Dictionary> dictionaryRoot = dictionaryCriteriaQuery.from(Dictionary.class);


        dictionaryCriteriaQuery.select(dictionaryRoot.get("id"))
                .where(criteriaBuilder.equal(dictionaryRoot.get("dictionaryType"), type));

        return em.createQuery(dictionaryCriteriaQuery).getSingleResult();
    }
}
