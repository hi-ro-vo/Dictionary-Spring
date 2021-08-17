package ru.test.dictionaries.dictionary;

import ru.test.dictionaries.entity.DictionaryEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDictionary {
    private Map<String, List<String>> map = new HashMap<>();

    private DictionaryEntity dictionaryEntity;

    public void loadFromMap(Map<String, List<String>> map) {//TODO: добавить проверку пришедшей мапы на валидность
        this.map = map;
    }

    public boolean delete(String key) {
        return map.remove(key) != null;
    }

    public boolean delete(String key, String value) {
        List<String> list = map.get(key);
        return list.remove(value);
    }

    public Optional<List<String>> find(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public void add(String key, String value) {
        if (isRuleFulfilled(key)) {
            List<String> list = map.get(key);
            list.add(value);
        } else {
            System.err.println(key + " doesn't suite the rule");
        }
    }

    public void setDictionaryEntity(DictionaryEntity dictionaryEntity) {
        this.dictionaryEntity = dictionaryEntity;
        dictionaryEntity
                .getEntries()
                .forEach((entity -> this.add(entity.getKeyValue(), entity.getValue())));
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public DictionaryEntity getDictionaryEntity() {
        return dictionaryEntity;
    }

    abstract public boolean isRuleFulfilled(String s);


}
