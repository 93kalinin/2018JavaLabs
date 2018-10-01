package prac2;

import java.util.Scanner;


/**
 * Отвечает за взаимодействие с пользователем. Также обрабатывает все исключительные ситуации,
 * возникающие в процессе работы программы.
 * @version 1.0.0 01.10.2018.
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
                    case "next":
                        System.out.println(testList.next());
                        break;
                    case "remove":
                        System.out.println("Удалено значение: " + testList.remove());
                        break;
                    case "peek":
                        System.out.println(testList.peek());
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Не удалось распознать команду или ее часть");
                }
            }
            catch (NumberFormatException e) {
                System.err.println("Не удалось распознать аргумент команды");
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}