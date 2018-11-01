package lab1;

import java.util.Arrays;
import java.util.Random;


final class Array2d {

    private static final Random random = new Random();
    private static int[][] array;


    private Array2d() {}

    private static void printByRows(int rowsAmount, int columnsAmount) {
        for (int rowIndex = 0; rowIndex < rowsAmount; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < columnsAmount; ++columnIndex)
                System.out.printf("%3d", array[columnIndex][rowIndex]);
            System.out.println();
        }   }

    static void initializeAndRun(int rowsAmount, int columnsAmount)
    throws IllegalArgumentException {
        if (rowsAmount <= 0  ||  columnsAmount <= 0)
            throw new IllegalArgumentException("Некорректная размерность массива");
        if (rowsAmount > 19  ||  columnsAmount > 19)
            throw new IllegalArgumentException("Недопустимо большое число строк или столбцов");
        final int ELEMENT_UPPER_BOUND = 100;

        array = new int[columnsAmount][rowsAmount];
        for (int columnIndex = 0; columnIndex < columnsAmount; ++columnIndex)
            for (int rowIndex = 0; rowIndex < rowsAmount; rowIndex++)
                array[columnIndex][rowIndex] = random.nextInt(ELEMENT_UPPER_BOUND);

        System.out.println("Создан массив: ");
        printByRows(rowsAmount, columnsAmount);
        for (int[] column : array)  Arrays.sort(column);
        System.out.println("После сортировки он принял следующий вид: ");
        printByRows(rowsAmount, columnsAmount);
    }   }
