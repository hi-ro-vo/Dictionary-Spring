package ru.test.dictionaries.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.dictionary.DictionaryFactory;
import ru.test.dictionaries.entity.DictionaryEntity;
import ru.test.dictionaries.entity.EntryEntity;

public class HibernateDictionaryDao implements DictionaryDao{

    private SessionFactory sessionFactory;
    private DictionaryFactory dictionaryFactory;
    private Session session;

    HibernateDictionaryDao(SessionFactory sessionFactory, DictionaryFactory dictionaryFactory){
        this.sessionFactory = sessionFactory;
        this.dictionaryFactory = dictionaryFactory;
    }

    private Session currentSession() {
        if (session == null){
            session = sessionFactory.openSession();
        }
        return session;
    }

    private void closeSession(){
        session.close();
        session = null;
    }

    @Override
    public AbstractDictionary getDictionary(DictionaryType type) {
        Transaction transaction = currentSession().beginTransaction();
        DictionaryEntity entity = (DictionaryEntity) currentSession().createCriteria(DictionaryEntity.class)
                                                    .add(Restrictions.eq("dictionaryType", type))
                                                    .uniqueResult();

        transaction.commit();
        //DictionaryEntity dictionaryEntity = (DictionaryEntity) currentSession().get(DictionaryEntity.class, type);
        AbstractDictionary dictionary = dictionaryFactory.getDictionary(type);
        dictionary.setDictionaryEntity(entity);
        return dictionary;
    }


    public EntryEntity getEntryFromDictionaryByKey(String key, DictionaryType type) {
        return null;
    }


    public void saveEntry(EntryEntity entity) {
        currentSession().beginTransaction();
        currentSession().save(entity);
        currentSession().getTransaction().commit();
    }

    @Override
    public void saveDictionary(AbstractDictionary dictionary) {

        currentSession().beginTransaction();

        currentSession().save(dictionary.getDictionaryEntity());
        currentSession().getTransaction().commit();
    }


}
