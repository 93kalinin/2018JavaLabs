package lab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public final class UserInterface {

    private static final Scanner userInput = new Scanner(System.in);
    private static Stack stack = null;


    private UserInterface() {}

    private static boolean stackIsAbsent() { return stack == null; }

    private static void parseCommand (String input)
    throws IllegalStateException, InputMismatchException {
        switch (input) {
            case "check":
                System.out.println(stack.isEmpty() ? "Стек пуст" : "Стек не пуст");  break;
            case "push":
                stack.push(userInput.nextInt());  break;
            case "depth":
                System.out.println(stack.depth(userInput.nextInt()));  break;
            case "peek":
                System.out.println(stack.peek());  break;
            case "pop":
                System.out.println(stack.pop());  break;
            case "pushAll":
                String[] intsToBeParsed = userInput.nextLine().trim().split(" ");
                for (String intToBeParsed : intsToBeParsed)
                    stack.push(Integer.parseInt(intToBeParsed));  break;
            case "show":
                System.out.println(Arrays.toString(stack.getStack()));  break;
            default:
                System.out.println("Не удалось распознать команду или ее часть");
                userInput.nextLine();
        }   }

    public static void main(String[] args) {
        try {
            String aboutLab = new String(Files.readAllBytes(Paths.get("./res/aboutLab2")));
            System.out.print(aboutLab);
            }
        catch (IOException ignored) {
            System.err.println("Невозможно прочитать файл с подсказкой о командах. Возможно, он"
                + "поврежден или отсутствует. Программа работает нормально. Введите команду или "
                + "exit чтобы выйти");
            }

        for (String input = userInput.next(); !input.equals("exit"); input = userInput.next()) {
            try {
                if (input.equals("create")) {
                    stack = new Stack(userInput.nextInt());
                    System.out.println("Стек создан. Теперь доступен полный набор команд");
                    }
                else if (stackIsAbsent()) {
                    System.out.println("Стек отсутствует. Допустима только команда create");
                    String ignoreEverythingElseUntilEndOfLine = userInput.nextLine();
                    }
                else parseCommand(input);
                }
            catch (InputMismatchException exception) {
                System.err.println("Некорректный аргумент команды: " + userInput.nextLine());
                }
            catch (IllegalArgumentException | IllegalStateException exception) {
                System.err.println(exception.getMessage());
                String ignoreEverythingElseUntilEndOfLine = userInput.nextLine();
    }   }   }   }