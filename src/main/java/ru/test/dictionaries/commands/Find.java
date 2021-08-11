package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.Optional;
import java.util.function.Supplier;

public class Find extends Command {
    private final Supplier<AbstractDictionary> Dictionary;
    private final String key;

    public Find(Supplier<AbstractDictionary> getCurrentDictionary, String key) {
        Dictionary = getCurrentDictionary;
        this.key = key;
    }


    @Override
    public void execute() {
        Optional<String> value = Dictionary.get().find(key);
        if (!value.isPresent()) {
            System.out.println("Значение для ключа " + key + " не найдено");
        } else {
            System.out.println(value.get());
        }
    }
}
