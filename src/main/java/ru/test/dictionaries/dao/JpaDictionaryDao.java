package ru.test.dictionaries.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Dictionary_;
import ru.test.dictionaries.entity.Entry;
import ru.test.dictionaries.entity.Entry_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

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
    public void addEntry(Entry entity) {
        try {
            getEntry(entity.getDictionary().getDictionaryType(), entity.getKeyValue(), entity.getValue());
        } catch (NoResultException e) {
            logger.debug("Add new entry: {}-{}", entity.getKeyValue(), entity.getValue());
            em.persist(entity);
        }
        logger.debug("Added entry: {}-{} already exist", entity.getKeyValue(), entity.getValue());
    }

    @Override
    public void editEntry(Entry entity) {
//        if (isSameEntryExist(entity)) return;
        try {
            getEntry(entity.getDictionary().getDictionaryType(), entity.getKeyValue(), entity.getValue());
        } catch (NonUniqueResultException e) {
            logger.debug("Edited entry: {}-{} already exist, removing", entity.getKeyValue(), entity.getValue());
            em.remove(entity);
            return;
        }
        logger.debug("Edit entry: {}-{}", entity.getKeyValue(), entity.getValue());
        em.merge(entity);
    }

    @Override
    public void saveDictionary(Dictionary dictionary) {

    }

    @Override
    public Entry getEntry(DictionaryType type, String key, String value) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Entry> entryCriteriaQuery = criteriaBuilder.createQuery(Entry.class);
        Root<Entry> entryRoot = entryCriteriaQuery.from(Entry.class);

        Join<Entry, Dictionary> join = entryRoot.join(Entry_.dictionary);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(join.get(Dictionary_.dictionaryType), type),
                        criteriaBuilder.equal(entryRoot.get(Entry_.keyValue), key),
                        criteriaBuilder.equal(entryRoot.get(Entry_.value), value)));

        Entry entry = em.createQuery(entryCriteriaQuery).getSingleResult();

        logger.debug("Read entry: {}-{}", entry.getKeyValue(), entry.getValue());
        return entry;
        //return new Entry();
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

    private Boolean isSameEntryExist(Entry entry) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Entry> entryCriteriaQuery = criteriaBuilder.createQuery(Entry.class);
        Root<Entry> entryRoot = entryCriteriaQuery.from(Entry.class);

        Join<Entry, Dictionary> join = entryRoot.join(Entry_.dictionary);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(entryRoot.get(Entry_.dictionary), entry.getDictionary()),
                        criteriaBuilder.equal(entryRoot.get(Entry_.keyValue), entry.getKeyValue()),
                        criteriaBuilder.equal(entryRoot.get(Entry_.value), entry.getValue())));

        List<Entry> entryList = em.createQuery(entryCriteriaQuery).getResultList();
        return !(entryList == null);
    }
}
