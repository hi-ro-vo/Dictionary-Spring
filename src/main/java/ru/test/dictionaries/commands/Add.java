package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.function.Supplier;

public class Add extends Command {

    private final Supplier<AbstractDictionary> Dictionary;
    private final String key;
    private final String value;

    public Add(Supplier<AbstractDictionary> getCurrentDictionary, String key, String value) {
        Dictionary = getCurrentDictionary;
        this.key = key;
        this.value = value;
    }


    @Override
    public void execute() {
        Dictionary.get().add(key, value);
    }
}
