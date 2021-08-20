package ru.test.dictionaries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.test.dictionaries.commands.*;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.exeptions.IllegalArgumentsCountException;
import ru.test.dictionaries.exeptions.NotACommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Supplier;

public class CommandFactory {

    private final Logger logger = LoggerFactory.getLogger(CommandFactory.class.getName());
    boolean resourceSeted = false;
    private final Supplier<AbstractDictionary> getCurrentDictionary;
    private final DictionariesController dictionariesController;


    CommandFactory(DictionariesController controller) {
        dictionariesController = controller;
        getCurrentDictionary = controller::getCurrentDictionary;
        resourceSeted = true;
    }


    public Command createCommand(CommandsEnum command, String... commandArguments) throws IllegalArgumentsCountException {
        Command result = null;
        switch (command) {
            case ADD: {
                if (commandArguments.length == 2) {
                    result = new Add(getCurrentDictionary, commandArguments[0], commandArguments[1]);
                }
                break;
            }
            case DELETE: {
                if (commandArguments.length == 1) {
                    result = new Delete(getCurrentDictionary, commandArguments[0]);
                }
                break;
            }
            case SHOW: {
                if (commandArguments.length == 0) {
                    result = new Show(getCurrentDictionary);
                }
                break;
            }
            case DIC: {
                if (commandArguments.length == 0) {
                    result = new ChangeDictionary(dictionariesController);
                }
                break;
            }
            case FIND: {
                if (commandArguments.length == 1) {
                    result = new Find(getCurrentDictionary, commandArguments[0]);
                }
                break;
            }
            case HELP: {
                if (commandArguments.length == 0) {
                    result = new Help();
                }
                break;
            }
            case EXIT: {
                if (commandArguments.length == 0) {
                    result = new Exit(dictionariesController);
                }
                break;
            }
            default: {
                IllegalStateException e = new IllegalStateException("Unexpected value: " + command);
                logger.error("Unexpected value: {}", command, e);
                throw e;

            }
        }

        if (result == null) {
            throw new IllegalArgumentsCountException(command + " содержит не подходящее число аргументов\n");
        }

        return result;
    }

    public Command createCommand(String... stringCommand) throws IllegalArgumentsCountException {
        CommandsEnum command;
        try {
            command = CommandsEnum.valueOf(stringCommand[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown command", e);
            IllegalArgumentException exception = new IllegalArgumentException("Unknown command: " + stringCommand[0], e);
            throw exception;
        }

        return createCommand(command, Arrays.copyOfRange(stringCommand, 1, stringCommand.length));
    }

    public Command readCommandFromReader(BufferedReader reader) throws IllegalArgumentsCountException, IOException, NotACommandException {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            throw e;
        }

        if (line.charAt(0) == '/') {
            String[] strings = line.substring(1).split(" ", 3);
            return createCommand(strings);
        } else {
            NotACommandException e = new NotACommandException("Команда должна начинаться с символа /");
            logger.warn(e.getMessage(), e);
            throw e;
        }


    }
}

