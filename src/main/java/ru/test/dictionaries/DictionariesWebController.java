package ru.test.dictionaries;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.test.dictionaries.dao.HibernateDictionaryDao;
import ru.test.dictionaries.entity.Dictionary;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class DictionariesWebController {

    @Autowired
    private HibernateDictionaryDao dictionaryDao;

    @Autowired
    private DictionariesService dictionaryService;


    @RequestMapping("/home")
    public String Hello(Model model, HttpSession session){
        if (session.getAttribute("currentType") == null) {
            session.setAttribute("currentType", DictionaryType.LATINIC_DICTIONARY);
        }

        model.addAttribute("entries", dictionaryService.getDictionary((DictionaryType) session.getAttribute("currentType")));
        return "index";
    }
}
