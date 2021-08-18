package ru.test.dictionaries;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.test.dictionaries.dao.HibernateDictionaryDao;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DictionariesWebController {

    @Autowired
    private HibernateDictionaryDao dictionaryDao;

    @Autowired
    private DictionariesService dictionaryService;


    @RequestMapping("/home")
    public String Hello(Model model, HttpSession session) {
        if (session.getAttribute("currentType") == null) {
            session.setAttribute("currentType", DictionaryType.LATINIC_DICTIONARY);
        }

        model.addAttribute("entries", dictionaryService.getDictionary((DictionaryType) session.getAttribute("currentType")));
        return "index";
    }

    @RequestMapping("/change")
    public String chengeDictionary(HttpSession session) {
        if (session.getAttribute("currentType") == DictionaryType.NUMERIC_DICTIONARY) {
            session.setAttribute("currentType", DictionaryType.LATINIC_DICTIONARY);
        } else {
            session.setAttribute("currentType", DictionaryType.NUMERIC_DICTIONARY);
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(HttpSession session) {
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Entry entry, HttpSession session) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        Dictionary currentDictionary = dictionaryDao.getDictionary(currentDictionaryType);
        if (currentDictionaryType.isRuleFulfilled(entry.getKeyValue())){
            entry.setDictionary(currentDictionary);
            currentDictionary.getEntries().add(entry);
            dictionaryDao.saveEntry(entry);
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/edit/{key}/{value}", method = RequestMethod.GET)
    public String editForm(@PathVariable String key, @PathVariable String value, HttpSession session, Model model) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        Entry entry = dictionaryDao.getEntry(currentDictionaryType, key, value);
        session.setAttribute("editedEntry", entry);
        model.addAttribute("key", key);
        model.addAttribute("value", value);
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Entry entry, HttpSession session) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        Dictionary currentDictionary = dictionaryDao.getDictionary(currentDictionaryType);

        Entry editedEntry = (Entry) session.getAttribute("editedEntry");

        if (!entry.getKeyValue().equals("")) {
            editedEntry.setKeyValue(entry.getKeyValue());
        }

        if (!entry.getValue().equals("")) {
            editedEntry.setValue(entry.getValue());
        }

        dictionaryDao.saveEntry(editedEntry);
        return "redirect:/home";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String remove(HttpSession session, HttpServletRequest request) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        dictionaryDao.removeEntity(currentDictionaryType, key, value);
        return "redirect:/home";
    }
}
