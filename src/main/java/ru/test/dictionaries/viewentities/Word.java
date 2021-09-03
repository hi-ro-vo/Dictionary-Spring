package ru.test.dictionaries.viewentities;

import org.slf4j.LoggerFactory;
import ru.test.dictionaries.DictionaryType;

import javax.validation.constraints.AssertTrue;
import java.util.List;

public class Word {


    private String foreign;

    private List<String> translations;

    private DictionaryType type;

    public Word() {
    }

    public Word(String foreignWord, List<String> translations, DictionaryType type) {
        this.foreign = foreignWord;
        this.translations = translations;
        this.type = type;
    }

    //TODO убрать
//    public Word(ForeignWord foreignWord) {
//        this.key = foreignWord.getKeyValue();
//        this.value = foreignWord.getValue();
//        this.type = foreignWord.getDictionaryType();
//    }

    @AssertTrue(message = "key didn't suite dictionary rule")
    public boolean isValid() {
        if (type == null || foreign == null) {
            return !false;
        }
        if (foreign.equals("")) {
            return true;
        }
        LoggerFactory.getLogger(Word.class).info("Is {} valid :{}", foreign, type.isRuleFulfilled(foreign));
        return type.isRuleFulfilled(foreign);
    }

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public DictionaryType getType() {
        return type;
    }

    public void setType(DictionaryType type) {
        this.type = type;
    }
}
