package lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;


public final class UserInterface {

    private static final Scanner userInput = new Scanner(System.in);

    private UserInterface() {}

    private static void run() {
        try { Array2d.initializeAndRun(userInput.nextInt(), userInput.nextInt()); }
        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            }
        catch (InputMismatchException ignored) {
            System.out.println("Некорректный аргумент команды: " + userInput.nextLine());
        }   }

    public static void main(String[] args) {
        try {
            String aboutLab = new String(Files.readAllBytes(Paths.get("./res/aboutLab1")));
            System.out.print(aboutLab);
            }
        catch (IOException ignored) {
            System.out.println("Невозможно прочитать файл с подсказкой о программе. "
                + "Возможно, он поврежден или отсутствует. Введите exit чтобы выйти.");
            }

        for (String input = userInput.next(); !input.equals("exit"); input = userInput.next()) {
            if(input.equals("run")) run();
            else  {
                String ignoreTrash = userInput.nextLine();
                System.out.println("Не удалось распознать команду. Попробуйте еще раз "
                + "или введите exit чтобы выйти");
    }   }   }   }