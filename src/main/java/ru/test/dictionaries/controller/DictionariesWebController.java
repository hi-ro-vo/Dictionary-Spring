package ru.test.dictionaries.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.test.dictionaries.DictionaryType;
import ru.test.dictionaries.service.DictionariesService;
import ru.test.dictionaries.viewentities.Word;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class DictionariesWebController {
    //TODO поиск по ключю или значению с выбором словаря
    //TODO current dictionary получать параметром сессии

    private final String CURRENT_DICTIONARY = "currentType";

    @Autowired
    private DictionariesService dictionaryService;


    @GetMapping("/home")
    public String getHomePage(Model model, HttpSession session) {
        if (session.getAttribute(CURRENT_DICTIONARY) == null) {
            session.setAttribute(CURRENT_DICTIONARY, DictionaryType.LATINIC_DICTIONARY);
        }

        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute(CURRENT_DICTIONARY);
        model.addAttribute("words", dictionaryService.getDictionary((DictionaryType) session.getAttribute(CURRENT_DICTIONARY)));
        model.addAttribute("type", currentDictionaryType);

        return "index";
    }

    @GetMapping("/change/{type}")
    public String changeDictionary(@PathVariable DictionaryType type, HttpSession session) {
        session.setAttribute(CURRENT_DICTIONARY, type);
        return "redirect:/home";

    }

    @GetMapping("/add")
    public String addForm(Model model, HttpSession session) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute(CURRENT_DICTIONARY);

        Word word = new Word();
        word.setType(currentDictionaryType);
        word.setTranslations(Arrays.asList(""));
        word.setForeign("");

        model.addAttribute("word", word);
        model.addAttribute("action", "add");
        return "addEdit";
    }

    @PostMapping(value = "/add")
    public String add(@Valid @ModelAttribute Word word, BindingResult br, HttpSession session, Model model) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute(CURRENT_DICTIONARY);

        if (br.hasErrors()) {//TODO логика при ошибке
            word.setForeign("");

            model.addAttribute("word", word);
            model.addAttribute("action", "add");
            return "addEdit";
        }

        dictionaryService.addWord(word);

        return "redirect:/home";
    }


    @GetMapping("/edit/{foreignWord}")
    public String editForm(@PathVariable String foreignWord, HttpSession session, Model model) {
        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute(CURRENT_DICTIONARY);
        Word word = dictionaryService.getWord(currentDictionaryType, foreignWord);
        session.setAttribute("editedWord", word);
        model.addAttribute("word", word);
        model.addAttribute("action", "edit");
        return "addEdit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Word word, BindingResult br, HttpSession session, Model model) {
        Word editedWord = (Word) session.getAttribute("editedWord");
        if (br.hasErrors()) {
            word.setForeign(editedWord.getForeign());
            model.addAttribute("word", preprocessNewWord(editedWord, word));
            model.addAttribute("action", "edit");
            return "addEdit";
        }
        dictionaryService.editWord(editedWord, word);
        return "redirect:/home";
    }

    private Word preprocessNewWord(Word oldWord, Word newWord) {
        for (int i = 0; i < oldWord.getTranslations().size(); i++) {
            String newTranslation = newWord.getTranslations().get(i);
            String oldTranslation = oldWord.getTranslations().get(i);
            if (newTranslation.equals("")) {
                newWord.getTranslations().set(i, oldTranslation);
            }
        }
        return newWord;
    }

//    @PostMapping("/remove")
//    @ResponseBody
//    public String remove(@RequestParam String key, @RequestParam String value, HttpSession session) {
//        DictionaryType currentDictionaryType = (DictionaryType) session.getAttribute(CURRENT_DICTIONARY);
//        dictionaryService.removeWord(new Word(key, value, currentDictionaryType));
//        return "success";
//    }

}
