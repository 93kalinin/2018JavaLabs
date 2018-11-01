package lab3;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static lab3.PropertyHandler.*;
import static lab3.Property.*;

/**
 * Отвечает за взаимодействие с пользователем. Также обрабатывает все исключительные ситуации,
 * возникающие в процессе работы программы.
 * @version 1.1 23.09.2018
 */
final class UserInterface {

    private static final Scanner userInput = new Scanner(System.in);
    private static final Path PROMPT_PATH = Paths.get("./res/aboutLab3");


    private UserInterface() {}

    private static void printPrompt() {
        try {
            String aboutLab = new String(Files.readAllBytes(PROMPT_PATH));
            System.out.print(aboutLab);
            }
        catch (IOException ignored) {
            System.err.println("Невозможно прочитать файл с подсказкой о командах. Возможно, он"
                + "поврежден или отсутствует. Программа работает нормально. Введите команду или "
                + "exit чтобы выйти");
        }   }

    private static void printEntry(Map.Entry<Identifier, Property> entry) {
        System.out.println(entry.getKey() + " " + entry.getValue()
                           + " tax: " + entry.getValue().calculateTax());
        }

    public static void main(String[] args) {
        printPrompt();

        while (true) {
            try {
                String[] input = userInput.nextLine().trim().split(" ");

                switch (input[0]) {
                    case "exit":
                        return;
                    case "add":
                        add(new Identifier(input[1]), Type.parse(input[2]), new BigDecimal(input[3]));
                        break;
                    case "forceAdd":
                        override(new Identifier(input[1]), Type.parse(input[2]),
                            new BigDecimal(input[3]));  break;
                    case "remove":
                        remove(new Identifier(input[1]));  break;
                    case "load":
                        load();  break;
                    case "set":
                        setTaxRate(Type.parse(input[1]), new BigDecimal(input[2]));  break;
                    case "show":
                        getProperties()
                            .filter(entry ->
                                entry.getValue().getType() == Type.parse(input[1]))
                            .forEach(UserInterface::printEntry);
                        break;
                    case "showAll":
                        getProperties()
                            .forEach(UserInterface::printEntry);
                        break;
                    case "find":
                        getProperties()
                            .filter(entry ->
                                entry
                                    .getKey()
                                    .equals(new Identifier(input[1])))
                            .forEach(UserInterface::printEntry);
                    case "save":
                        save();  break;
                    case "help":
                        printPrompt();  break;
                    default:
                        System.out.println("Не удалось распознать команду или ее часть");
                }   }
            catch (IndexOutOfBoundsException exception) {
                System.err.println("Недостаточно аргументов для данной команды. Для повторного "
                    + "просмотра доступных команд и их аргументов введите help. Для выхода exit");
                }
            catch (IOException exception) {
                System.err.println("Не удалось открыть файл базы данных");
                }
            catch (NumberFormatException exception) {
                System.err.println("Задано некорректное числовое значение в одном из аргументов");
                }
            catch (IllegalArgumentException | NoSuchElementException exception) {
                System.err.println(exception.getMessage());
            }   }   }   }