package ru.test.dictionaries;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.test.dictionaries.dao.HibernateDictionaryDao;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.entity.DictionaryEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class DictionariesWebController {

    HibernateDictionaryDao dictionaryDao;

    @Autowired
    DictionariesWebController(HibernateDictionaryDao dictionaryDao){
        this.dictionaryDao = dictionaryDao;
    }

    @RequestMapping("/home")
    public String Hello(Model model){
        AbstractDictionary dictionary = dictionaryDao.getDictionary(DictionaryType.LATINIC_DICTIONARY);
        List<DictionaryEntity> list = new LinkedList<>();
        list.add(dictionary.getDictionaryEntity());
        model.addAttribute("dictionaries", list);
        return "index";
    }
}
