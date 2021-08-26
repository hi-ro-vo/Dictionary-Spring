package ru.test.dictionaries.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.test.dictionaries.DictionariesService;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.entity.Dictionary;
import ru.test.dictionaries.entity.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class DictionariesWebController {


    @Autowired
    private DictionariesService dictionaryService;


    @RequestMapping("/home")
    public String Hello(Model model, HttpSession session) {
        if (session.getAttribute("currentType") == null) {
            session.setAttribute("currentType", DictionaryType.LATINIC_DICTIONARY);
        }

        model.addAttribute("entries", dictionaryService.getDictionary((DictionaryType) session.getAttribute("currentType")).getEntries());
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
    public String addForm(Model model, HttpSession session, Entry entry) {

        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");

        model.addAttribute("type", currentDictionaryType);
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute Entry entry, BindingResult br, HttpSession session, Model model) {
        if (br.hasErrors()) {
            return addForm(model, session, entry);
        }

        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");

        dictionaryService.addEntry(currentDictionaryType, entry.getKeyValue(), entry.getValue());

        return "redirect:/home";
    }

    @RequestMapping(value = "/edit/{key}/{value}", method = RequestMethod.GET)
    public String editForm(@PathVariable String key, @PathVariable String value, HttpSession session, Model model) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        Entry entry = dictionaryService.getEntry(currentDictionaryType, key, value);
        session.setAttribute("editedEntry", entry);
        model.addAttribute("key", key);
        model.addAttribute("value", value);
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Entry entry, HttpSession session) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        Dictionary currentDictionary = dictionaryService.getDictionary(currentDictionaryType);

        Entry editedEntry = (Entry) session.getAttribute("editedEntry");

        if (!entry.getKeyValue().equals("")) {
            editedEntry.setKeyValue(entry.getKeyValue());
        }

        if (!entry.getValue().equals("")) {
            editedEntry.setValue(entry.getValue());
        }

        dictionaryService.saveEntry(editedEntry);
        return "redirect:/home";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String remove(HttpSession session, HttpServletRequest request) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute("currentType");
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        dictionaryService.removeEntry(currentDictionaryType, key, value);
        return "redirect:/home";
    }
}
