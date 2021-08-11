package ru.test.dictionaries.dao;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileController implements DictionaryDAO {
    static final private Logger logger = Logger.getLogger(FileController.class.getName());
    private final AbstractDictionary dictionary;
    private String filePath;

    public FileController(AbstractDictionary dictionary, String filePath) {
        this.dictionary = dictionary;
        this.filePath = filePath;
    }

    private Map<String, String> readFromFile(String filePath, Predicate<String> isRuleFulfilled) throws IOException {
        Map<String, String> result = new HashMap<>();
        String line;
        try {

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0];
                    String value = parts[1];
                    if (parts.length == 2 && isRuleFulfilled.test(key)) {
                        result.put(key, value);
                    } else {
                        System.err.println("ignoring line: " + line);
                        logger.log(Level.INFO, "ignoring line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.FINE, "При чтении файла " + filePath + " произошла ошибка", e);
            throw e;

        }
        return result;
    }

    private boolean saveToFile(String filePath, Map<String, String> map) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "При записи в файл " + filePath + " произошла ошибка", e);
            return false;
        }

        return true;
    }

    @Override
    public void save() {
        saveToFile(filePath, dictionary.getMap());
    }

    @Override
    public void load() throws IOException {
        Map<String, String> map = readFromFile(filePath, dictionary::isRuleFulfilled);
        dictionary.loadFromMap(map);
    }

    @Override
    public void setProperty(String property) {
        if (property != ""){
            filePath = property;
        }
    }
}
