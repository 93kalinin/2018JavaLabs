package prac1;


/**
 * Вычислить среднее арифметическое массива чисел типа double.
 * @version 1.0  25.09.2018
 */
final public class ArithmeticMean {

    private ArithmeticMean() {}

    public static double of(double[] input) {
        if (input == null  ||  input.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым или null");
        }
        double sum = 0;
        for (double number : input) sum += number;
        if (Double.isInfinite(sum)) {
            throw new IllegalArgumentException("Полученное число слишком велико и не "
                + "может быть вычислено точно.");
        }
        return sum / input.length;
    }
}
