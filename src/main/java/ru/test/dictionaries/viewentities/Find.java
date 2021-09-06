package ru.test.dictionaries.viewentities;

public class Find {
    String request;
    Boolean isTranslation = false;
    Boolean isFindInAllPlaces = false;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Boolean getTranslation() {
        return isTranslation;
    }

    public void setTranslation(Boolean translation) {
        isTranslation = translation;
    }

    public Boolean getFindInAllPlaces() {
        return isFindInAllPlaces;
    }

    public void setFindInAllPlaces(Boolean findInAllPlaces) {
        isFindInAllPlaces = findInAllPlaces;
    }
}
