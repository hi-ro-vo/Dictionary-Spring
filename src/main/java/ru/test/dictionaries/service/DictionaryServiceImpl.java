package ru.test.dictionaries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.entity.ForeignWord;
import ru.test.dictionaries.entity.TranslatedWord;
import ru.test.dictionaries.viewentities.Word;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionariesService {

    public static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    @Qualifier("JpaDictionaryDao")
    //@Qualifier("HibernateDictionaryDao")
    DictionaryDao dictionaryDao;

    private static Word foreignWordToWord(ForeignWord foreignWord) {
        Word word = new Word();
        word.setForeign(foreignWord.getWord());
        word.setType(foreignWord.getDictionaryType());
        List<String> translations = foreignWord.getTranslatedWords().stream()
                .map(TranslatedWord::getValue)
                .collect(Collectors.toList());
        word.setTranslations(translations);
        return word;
    }

    @Override
    public void addWord(Word word) {
        ForeignWord foreignWord = dictionaryDao.getForeignWord(word.getType(), word.getForeign());

        for (String translation : word.getTranslations()) {
            TranslatedWord translatedWord = dictionaryDao.getTranslatedWord(foreignWord, translation);
        }
    }

    @Override
    public void removeWord(Word word) {
//        dictionaryDao.removeEntity(word.getType(), word.getKey(), word.getValue());
    }

    @Override
    public void editWord(Word oldWord, Word newWord) {
        ForeignWord foreignWord = dictionaryDao.getForeignWord(oldWord.getType(), oldWord.getForeign());

        if (!newWord.getForeign().equals("")) {
            foreignWord.setWord(newWord.getForeign());
        }

        for (int i = 0; i < newWord.getTranslations().size(); i++) {
            String newTranslation = newWord.getTranslations().get(i);
            String oldTranslation = "";
            if (oldWord.getTranslations().size() > i) {
                oldTranslation = oldWord.getTranslations().get(i);
            }


            if (!newTranslation.equals("")) {
                TranslatedWord newTranslatedWord = getTranslatedWord(foreignWord, oldTranslation);
                newTranslatedWord.setValue(newTranslation);
            }
        }

    }

    @Override
    public void saveWord(Word word) {
//        dictionaryDao.addForeignWord(new ForeignWord(word.getType(), word.getKey(), word.getValue()));
    }

    @Override
    public Word getWord(DictionaryType type, String foreignWord) {
        return foreignWordToWord(dictionaryDao.getForeignWord(type, foreignWord));
    }

    @Override
    public List<Word> getDictionary(DictionaryType type) {
        return dictionaryDao.getDictionary(type).stream()
                .map(DictionaryServiceImpl::foreignWordToWord)
                .collect(Collectors.toList());
    }

    private TranslatedWord getTranslatedWord(ForeignWord foreignWord, String translation) {
        Optional<TranslatedWord> translatedWord = foreignWord.getTranslatedWords().stream()
                .filter(trWord -> trWord.getValue().equals(translation))
                .findFirst();

        if (translatedWord.isPresent()) {
            return translatedWord.get();
        } else {
            TranslatedWord newTranslatedWord = new TranslatedWord();
            foreignWord.getTranslatedWords().add(newTranslatedWord);
            newTranslatedWord.setForeignWord(foreignWord);
            dictionaryDao.addTranslatedWord(newTranslatedWord);
            return newTranslatedWord;
        }
    }
}
