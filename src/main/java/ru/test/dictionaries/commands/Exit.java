package ru.test.dictionaries.commands;

import ru.test.dictionaries.DictionariesController;

public class Exit extends Command {
    private final DictionariesController dictionariesController;

    public Exit(DictionariesController controller) {
        dictionariesController = controller;
    }


    @Override
    public void execute() {
        dictionariesController.exit();
    }
}
