package ru.test.dictionaries.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

//@Transactional(propagation = Propagation.REQUIRES_NEW)
public class HibernateDictionaryDao implements DictionaryDao {

    private final SessionFactory sessionFactory;
    private Session session;

    public HibernateDictionaryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    private Session currentSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    private void closeSession() {
        session.close();
        session = null;
    }

    @Override
    public Dictionary getDictionary(DictionaryType type) {
//        currentSession().beginTransaction();
        Dictionary entity = (Dictionary) currentSession().createCriteria(Dictionary.class)
                .add(Restrictions.eq("dictionaryType", type))
                .uniqueResult();

        //currentSession().getTransaction().commit();
        return entity;
    }

    @Override
    public void addEntry(Entry entity) {
        //currentSession().beginTransaction();
        currentSession().save(entity);
        //currentSession().getTransaction().commit();
    }

    @Override
    public void editEntry(Entry entity) {

    }

    @Override
    public void saveDictionary(Dictionary dictionary) {

//        currentSession().beginTransaction();

        currentSession().save(dictionary);
//        currentSession().getTransaction().commit();
    }

    @Override
    public Entry getEntry(DictionaryType type, String key, String value) {

        Dictionary dictionary = (Dictionary) currentSession().createCriteria(Dictionary.class)
                .add(Restrictions.eq("dictionaryType", type)).uniqueResult();
        Entry entry = (Entry) currentSession().createCriteria(Entry.class)
                .add(Restrictions.and(Restrictions.eq("keyValue", key),
                        Restrictions.eq("value", value),
                        Restrictions.eq("dictionary", dictionary)))
                .uniqueResult();

        return entry;
    }

    @Override
    public void removeEntity(DictionaryType type, String key, String value) {
//        Transaction transaction = currentSession().beginTransaction();
        Entry entry = getEntry(type, key, value);
        entry.getDictionary().getEntries().remove(entry);
        currentSession().delete(entry);

//        transaction.commit();
    }

}
