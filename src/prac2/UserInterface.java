package prac2;

import java.util.Scanner;


/**
 * Отвечает за взаимодействие с пользователем. Также обрабатывает все исключительные ситуации,
 * возникающие в процессе работы программы.
 * @version 0.1 30.09.2018
 */
final class UserInterface {

    private static MyList<String> testList = new MyList<>();
    private static final Scanner userInput = new Scanner(System.in);


    private UserInterface() {}

    public static void main(String[] args) {

        while (true) {
            try {
                String[] input = userInput.nextLine().trim().split(" ");
                switch (input[0]) {
                    case "insert":
                        for (int i = 1; i < input.length; ++i)  testList.insert(input[i]);
                        break;
                    case "advance":
                        testList.advance(Integer.parseInt(input[1]));
                        break;
                    case "show":
                            System.out.println(testList);
                        break;
                    case "clear":
                        testList = new MyList<>();
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Не удалось распознать команду или ее часть");
                }
            }
            catch (IndexOutOfBoundsException e) {
                System.err.println("Недостаточно аргументов для данной команды");
            }
            catch (NumberFormatException e) {
                System.err.println("Не удалось распознать аргумент команды");
            }
            catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}