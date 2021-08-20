package ru.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.test.dictionaries.DictionariesController;

public class Mmtr {


    public static void main(String[] args) {
        String firstFilePath = "src/main/resources/dic1.txt";
        String secondFilePath = "src/main/resources/dic2.txt";
        if (args.length == 2) {
            firstFilePath = args[0];
            secondFilePath = args[1];
        }

        System.setProperty("latinic", firstFilePath);
        System.setProperty("numeric", secondFilePath);

        ApplicationContext context = new ClassPathXmlApplicationContext("dictionaries.xml");

        DictionariesController controller = (DictionariesController) context.getBean("dictionaries");



        controller.start();

    }
}
