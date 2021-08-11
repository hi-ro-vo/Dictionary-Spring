package ru.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.test.dictionaries.DictionariesController;

public class Mmtr {


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("dictionaries.xml");

        DictionariesController controller = (DictionariesController) context.getBean("dictionaries");

        String firstFilePath = "";
        String secondFilePath = "";
        if (args.length == 2) {
            firstFilePath = args[0];
            secondFilePath = args[1];
        }

        controller.start(firstFilePath, secondFilePath);

    }
}
