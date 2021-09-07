package ru.test.dictionaries.viewentities;

public class Find {
    String request;
    String isTranslation = "false";
    String isFindInAllPlaces = "false";

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getIsTranslation() {
        return isTranslation;
    }

    public void setIsTranslation(String isTranslation) {
        this.isTranslation = isTranslation;
    }

    public String getIsFindInAllPlaces() {
        return isFindInAllPlaces;
    }

    public void setIsFindInAllPlaces(String isFindInAllPlaces) {
        this.isFindInAllPlaces = isFindInAllPlaces;
    }
}
