package ru.test.dictionaries.commands;

import ru.test.dictionaries.DictionariesController;

public class ChangeDictionary extends Command {
    private final DictionariesController dictionariesController;

    public ChangeDictionary(DictionariesController controller) {
        dictionariesController = controller;
    }


    @Override
    public void execute() {
        dictionariesController.changeCurrentDictionary();
    }
}
