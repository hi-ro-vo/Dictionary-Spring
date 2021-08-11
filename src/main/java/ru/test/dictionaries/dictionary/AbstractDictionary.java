package ru.test.dictionaries.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDictionary {
    private Map<String, String> map = new HashMap<>();

    public void loadFromMap(Map<String, String> map) {//TODO: добавить проверку пришедшей мапы на валидность
        this.map = map;
    }

    public boolean delete(String key) {
        return map.remove(key) != null;
    }

    public Optional<String> find(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public void add(String key, String value) {
        if (isRuleFulfilled(key)) {
            map.put(key, value);
        } else {
            System.err.println(key + " doesn't suite the rule");
        }
    }

    public Map<String, String> getMap() {
        return map;
    }

    abstract public boolean isRuleFulfilled(String s);

}
