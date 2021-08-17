package ru.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.test.dictionaries.DictionariesController;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.entity.DictionaryEntity;
import ru.test.dictionaries.entity.EntryEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Mmtr {


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("dictionaries.xml");

        DictionariesController controller = (DictionariesController) context.getBean("dictionaries");


        controller.start("", "");

        DictionaryDao dictionaryDao = (DictionaryDao) context.getBean("dictionaryDao");
        AbstractDictionary dictionary = (AbstractDictionary) context.getBean("latinicDictionary");
        AbstractDictionary dictionary1 = (AbstractDictionary) context.getBean("numericDictionary");

        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setEntries(new LinkedList<>());
        dictionaryEntity.setId(1l);

        //EntryDao entryDao = (EntryDao) context.getBean("entityDao");
        List<EntryEntity> list = dictionary.getMap()
                .entrySet()
                .stream()
                .map(
                        (p) -> {
                            EntryEntity e = new EntryEntity();
                            e.setKeyValue(p.getKey());
                            e.setValue(p.getValue());
                            //e.setDictionaryEntity(dictionaryEntity);
                            //entryDao.saveEntry(e);
                            return e;
                        })
                .collect(Collectors.toList());

        //list.forEach(entryDao::saveEntry);

        dictionaryEntity.setD(DictionaryType.LATINIC_DICTIONARY.toString());
        dictionaryEntity.setEntries(list);
        dictionaryEntity.setDictionaryType(DictionaryType.LATINIC_DICTIONARY);
        dictionary.setDictionaryEntity(dictionaryEntity);
        dictionaryDao.saveDictionary(dictionary);
        //dictionaryEntity.setEntries(list);


        dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setEntries(dictionary1.getMap()
                .entrySet()
                .stream()
                .map(
                        (p) -> {
                            EntryEntity e = new EntryEntity();
                            e.setKeyValue(p.getKey());
                            e.setValue(p.getValue());
                            //e.setDictionaryEntity(dictionaryEntity);
                            //entryDao.saveEntry(e);
                            return e;
                        })
                .collect(Collectors.toList()));
        dictionaryEntity.setDD(DictionaryType.NUMERIC_DICTIONARY.toString());
        dictionaryEntity.setDictionaryType(DictionaryType.NUMERIC_DICTIONARY);
        dictionary1.setDictionaryEntity(dictionaryEntity);
        dictionaryDao.saveDictionary(dictionary1);

//        DictionaryDao dictionaryDao2= (DictionaryDao) context.getBean("JpaDictionaryDao");
//        AbstractDictionary dictionary2 = dictionaryDao2.getDictionary(DictionaryType.LATINIC_DICTIONARY);
//        dictionary2.getDictionaryEntity();
    }
}
