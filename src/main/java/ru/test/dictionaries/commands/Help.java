package ru.test.dictionaries.commands;

public class Help extends Command {


    public Help() {
    }

    public void showHelp(CommandsEnum command) {
        String message;
        switch (command) {
            case ADD:
                message = "/add [key, value] команда для добавления новой пары в словарь";
                break;
            case DIC:
                message = "/dic команда для смены словаря";
                break;
            case HELP:
                message = "/help выводит список доступных команд";
                break;
            case EXIT:
                message = "/exit завершение программы";
                break;
            case FIND:
                message = "/find [key] команда для поиска значения в словаре по ключу";
                break;
            case SHOW:
                message = "/show команда для вывода всего словаря на экран";
                break;
            case DELETE:
                message = "/delete [key] команда для удаления пары из словаря по ключу";
                break;
            default:
                message = "Для команды: " + command + " нет подсказки";
        }

        System.out.println(message);

    }

    @Override
    public void execute() {
        for (CommandsEnum command : CommandsEnum.values()) {
            showHelp(command);
        }
    }
}
