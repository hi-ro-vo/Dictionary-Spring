package ru.test.dictionaries.dao;

import java.io.IOException;

public interface FileInterface {
    void save();

    void load() throws IOException;

    void setProperty(String property);
}
