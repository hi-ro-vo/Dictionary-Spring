package ru.test.dictionaries;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ru.test.dictionaries.commands.Command;
import ru.test.dictionaries.commands.CommandsEnum;
import ru.test.dictionaries.dao.DictionaryDAO;
import ru.test.dictionaries.dao.FileController;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.dictionary.LatinicDictionary;
import ru.test.dictionaries.dictionary.NumericDictionary;
import ru.test.dictionaries.exeptions.IllegalArgumentsCountException;
import ru.test.dictionaries.exeptions.NotACommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("Dictionaries")
public class DictionariesController implements ApplicationContextAware {
    private static final  Logger logger = Logger.getLogger(DictionariesController.class.getName());

    private AbstractDictionary latinicDictionary;
    private AbstractDictionary numericDictionary;
    private boolean exitFlag = false;
    private DictionaryType currentDictionary = DictionaryType.LATINIC_DICTIONARY;
    private ApplicationContext context;

    public DictionariesController() {
        logger.setLevel(Level.ALL);
    }

    public AbstractDictionary getCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            return latinicDictionary;
        } else {
            return numericDictionary;
        }
    }

    public void changeCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            currentDictionary = DictionaryType.NUMERIC_DICTIONARY;
        } else {
            currentDictionary = DictionaryType.LATINIC_DICTIONARY;
        }
    }

    public void exit() {
        exitFlag = true;
    }

    public void start(String firstFilePath, String secondFilePath) {

        DictionaryDAO latinicDAO = (DictionaryDAO) context.getBean("latinicFileDAO");
        latinicDAO.setProperty(firstFilePath);
        DictionaryDAO numericDAO =  (DictionaryDAO) context.getBean("numericFileDAO");
        latinicDAO.setProperty(secondFilePath);

        try {
            latinicDAO.load();
            numericDAO.load();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Loading from DAO error: ", e);
            return;
        }

        CommandFactory commandFactory = context.getBean(CommandFactory.class);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            commandFactory.createCommand(CommandsEnum.HELP).execute();
        } catch (IllegalArgumentsCountException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }

        exitFlag = false;

        while (!exitFlag) {
            try {
                Command command = commandFactory.readCommandFromReader(in);
                command.execute();
            } catch (IllegalArgumentException | IOException | IllegalArgumentsCountException | NotACommandException e) {
                System.err.println(e.getMessage());
            }
        }

        latinicDAO.save();
        numericDAO.save();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public enum DictionaryType {
        LATINIC_DICTIONARY, NUMERIC_DICTIONARY
    }

    public AbstractDictionary getLatinicDictionary() {
        return latinicDictionary;
    }

    public void setLatinicDictionary(LatinicDictionary latinicDictionary) {
        this.latinicDictionary = latinicDictionary;
    }

    public AbstractDictionary getNumericDictionary() {
        return numericDictionary;
    }

    public void setNumericDictionary(NumericDictionary numericDictionary) {
        this.numericDictionary = numericDictionary;
    }
}
