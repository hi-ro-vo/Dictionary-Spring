package ru.test.dictionaries.entity;

import javax.persistence.*;

@Entity(name = "translated_word")
public class TranslatedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "forreing_word_id", nullable = false)
    private ForeignWord foreignWord;
    private String value;

    public TranslatedWord() {
    }

    public ForeignWord getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(ForeignWord foreignWord) {
        this.foreignWord = foreignWord;
    }

//    public TranslatedWord(DictionaryType dictionaryType, String key, String value){
//        this.dictionaryType = dictionaryType;
//        this.keyValue = key;
//        this.value = value;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
