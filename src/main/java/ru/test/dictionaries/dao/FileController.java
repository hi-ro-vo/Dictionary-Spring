package ru.test.dictionaries.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;;

public class FileController implements DictionaryDAO {
    static final private Logger logger = LoggerFactory.getLogger(FileController.class.getName());
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
                        logger.info("ignoring line: {}", line);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("При чтении файла {} произошла ошибка", filePath, e);
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
            logger.warn("При записи в файл {} произошла ошибка", filePath, e);
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

}
