package ru.test.dictionaries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.entity.Entry;
import ru.test.dictionaries.viewentities.Word;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionariesService {

    public static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    @Qualifier("JpaDictionaryDao")
    DictionaryDao dictionaryDao;


    @Override
    public void addWord(Word word) {
        if (word.isValid()) {
            Entry entry = new Entry(word, dictionaryDao.getDictionary(word.getType()));
            dictionaryDao.addEntry(entry);
        } else {
            logger.warn("Can't add {} to {}.", word.getKey(), word.getType());
        }
    }

    @Override
    public void removeWord(Word word) {
        dictionaryDao.removeEntity(word.getType(), word.getKey(), word.getValue());
    }

    @Override
    public void editWord(Word oldWord, Word newWord) {
        Entry entry = dictionaryDao.getEntry(oldWord.getType(), oldWord.getKey(), oldWord.getValue());

        if (!newWord.getKey().equals("")) {
            entry.setKeyValue(newWord.getKey());
        }

        if (!newWord.getValue().equals("")) {
            entry.setValue(newWord.getValue());
        }

        dictionaryDao.editEntry(entry);
    }

    @Override
    public void saveWord(Word word) {
        dictionaryDao.addEntry(new Entry(word, dictionaryDao.getDictionary(word.getType())));
    }

    @Override
    public Word getWord(DictionaryType type, String key, String value) {
        return new Word(dictionaryDao.getEntry(type, key, value));
    }

    @Override
    public Set<Word> getDictionary(DictionaryType type) {
        return dictionaryDao.getDictionary(type).getEntries().stream().map((e) -> {
            Word w = new Word();
            w.setKey(e.getKeyValue());
            w.setType(e.getDictionary().getDictionaryType());
            w.setValue(e.getValue());
            return w;
        }).collect(Collectors.toSet());

    }
}
