package ru.test.dictionaries.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.ForeignWord;
import ru.test.dictionaries.entity.ForeignWord_;
import ru.test.dictionaries.entity.TranslatedWord;
import ru.test.dictionaries.entity.TranslatedWord_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;

@Repository("JpaDictionaryDao")
@Transactional
public class JpaDictionaryDao implements DictionaryDao {

    private static final Logger logger = LoggerFactory.getLogger(JpaDictionaryDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<ForeignWord> getDictionary(DictionaryType type) {
        //Dictionary entity = em.find(Dictionary.class, 1L);
        logger.debug("Read dictionary: {}", type);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ForeignWord> dictionaryCriteriaQuery = criteriaBuilder.createQuery(ForeignWord.class);

        Root<ForeignWord> dictionaryRoot = dictionaryCriteriaQuery.from(ForeignWord.class);
        dictionaryCriteriaQuery.where(criteriaBuilder.equal(dictionaryRoot.get(ForeignWord_.dictionaryType), type));

        return em.createQuery(dictionaryCriteriaQuery).getResultList();
    }

    @Override
    public void addForeignWord(ForeignWord entity) {
        em.persist(entity);
    }

    @Override
    public void editEntry(ForeignWord entity) {
//        if (isSameEntryExist(entity)) return;
//        try {
//            getEntry(entity.getDictionaryType(), entity.getKeyValue(), entity.getValue());
//        } catch (NonUniqueResultException e) {
//            logger.debug("Edited entry: {}-{} already exist, removing", entity.getKeyValue(), entity.getValue());
//            em.remove(entity);
//            return;
//        }
//        logger.debug("Edit entry: {}-{}", entity.getKeyValue(), entity.getValue());
//        em.merge(entity);
    }

    @Override
    public ForeignWord getForeignWord(DictionaryType type, String foreignWord) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ForeignWord> entryCriteriaQuery = criteriaBuilder.createQuery(ForeignWord.class);
        Root<ForeignWord> entryRoot = entryCriteriaQuery.from(ForeignWord.class);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(entryRoot.get(ForeignWord_.dictionaryType), type),
                        criteriaBuilder.equal(entryRoot.get(ForeignWord_.word), foreignWord)));


        ForeignWord result;
        try {
            result = em.createQuery(entryCriteriaQuery).getSingleResult();
            logger.debug("Read ForeignWord: {}", result.getWord());
        } catch (NoResultException noResultException) {
            result = new ForeignWord();
            result.setDictionaryType(type);
            result.setTranslatedWords(new HashSet<>());
            result.setWord(foreignWord);
            em.persist(result);
            logger.debug("Create ForeignWord: {}", result.getWord());
        }

        return result;
    }

    @Override
    public void removeEntity(DictionaryType type, String key, String value) {
        logger.debug("Removed entry: {}-{}", key, value);
        ForeignWord foreignWord = getForeignWord(type, key);
        TranslatedWord translatedWord = getTranslatedWord(foreignWord, value);
        foreignWord.getTranslatedWords().remove(translatedWord);
        em.remove(translatedWord);
        if (foreignWord.getTranslatedWords().isEmpty()) {
            em.remove(foreignWord);
        }
    }

    @Override
    public void addTranslatedWord(TranslatedWord translatedWord) {
        em.persist(translatedWord);
    }

    @Override
    public TranslatedWord getTranslatedWord(ForeignWord foreignWord, String translation) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<TranslatedWord> entryCriteriaQuery = criteriaBuilder.createQuery(TranslatedWord.class);
        Root<TranslatedWord> entryRoot = entryCriteriaQuery.from(TranslatedWord.class);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(entryRoot.get(TranslatedWord_.FOREIGN_WORD), foreignWord),
                        criteriaBuilder.equal(entryRoot.get(TranslatedWord_.VALUE), translation)));

        TranslatedWord result;

        try {
            result = em.createQuery(entryCriteriaQuery).getSingleResult();
            logger.debug("Read TranslatedWord: {}", result.getValue());
        } catch (NoResultException noResultException) {
            result = new TranslatedWord();
            result.setValue(translation);
            result.setForeignWord(foreignWord);
            foreignWord.getTranslatedWords().add(result);
            em.persist(result);
            logger.debug("Create TranslatedWord: {}", result.getValue());
        }


        return result;
    }

    @Override
    public List<ForeignWord> findByForeignInAllPlaces(String foreignWord) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ForeignWord> entryCriteriaQuery = criteriaBuilder.createQuery(ForeignWord.class);
        Root<ForeignWord> entryRoot = entryCriteriaQuery.from(ForeignWord.class);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.like(entryRoot.get(ForeignWord_.WORD), "%" + foreignWord + "%"));

        return em.createQuery(entryCriteriaQuery).getResultList();
    }

    @Override
    public List<TranslatedWord> findByTranslationInAllPlaces(String translation) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TranslatedWord> entryCriteriaQuery = criteriaBuilder.createQuery(TranslatedWord.class);

        Root<TranslatedWord> entryRoot = entryCriteriaQuery.from(TranslatedWord.class);

        entryCriteriaQuery.where(criteriaBuilder.like(entryRoot.get(TranslatedWord_.VALUE), "%" + translation + "%"));

        return em.createQuery(entryCriteriaQuery).getResultList();
    }

    @Override
    public List<TranslatedWord> findByTranslationInDictionary(DictionaryType type, String translation) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TranslatedWord> entryCriteriaQuery = criteriaBuilder.createQuery(TranslatedWord.class);

        Root<TranslatedWord> entryRoot = entryCriteriaQuery.from(TranslatedWord.class);
        Join<TranslatedWord, ForeignWord> translatedWordForeignWordJoin = entryRoot.join(TranslatedWord_.FOREIGN_WORD);

        entryCriteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.like(entryRoot.get(TranslatedWord_.VALUE), "%" + translation + "%"),
                criteriaBuilder.equal(translatedWordForeignWordJoin.get(ForeignWord_.DICTIONARY_TYPE), type)));
        return em.createQuery(entryCriteriaQuery).getResultList();
    }

    @Override
    public List<ForeignWord> findByForeignInDictionary(DictionaryType type, String foreignWord) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ForeignWord> entryCriteriaQuery = criteriaBuilder.createQuery(ForeignWord.class);
        Root<ForeignWord> entryRoot = entryCriteriaQuery.from(ForeignWord.class);

        entryCriteriaQuery.select(entryRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.like(entryRoot.get(ForeignWord_.WORD), "%" + foreignWord + "%"),
                        criteriaBuilder.equal(entryRoot.get(ForeignWord_.DICTIONARY_TYPE), type)
                ));

        return em.createQuery(entryCriteriaQuery).getResultList();
    }

}
