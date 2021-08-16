package ru.test.dictionaries.dictionary;

import ru.test.dictionaries.DictionaryType;

public class DictionaryFactory {
    private AbstractDictionary latinicDictionary;
    private AbstractDictionary numericDictionary;

    public DictionaryFactory(AbstractDictionary latinicDictionary, AbstractDictionary numericDictionary){
        this.latinicDictionary = latinicDictionary;
        this.numericDictionary = numericDictionary;
    }

    public AbstractDictionary getDictionary(DictionaryType type){
        if (type == DictionaryType.LATINIC_DICTIONARY) {
            return latinicDictionary;
        } else {
            return numericDictionary;
        }
    }


}
