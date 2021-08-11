package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.function.Supplier;

public class Delete extends Command {

    private final Supplier<AbstractDictionary> Dictionary;
    private final String key;

    public Delete(Supplier<AbstractDictionary> getCurrentDictionary, String key) {
        Dictionary = getCurrentDictionary;
        this.key = key;
    }


    @Override
    public void execute() {
        if (Dictionary.get().delete(key))
            System.err.println("В словаре нет ключа" + key);
    }
}
