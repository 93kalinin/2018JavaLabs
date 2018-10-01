package prac2;

import java.util.Optional;

/**
 * Реализует односвязный циклический список.
 * @param <V> обозначает тип содержимого ячейки списка.
 * @version 1.0.0 01.10.2018.
 */
public class MyList<V> {

    private class Cell {
        Cell next;
        V value;
    }


    private Cell current;
    private int size;

    /*
    Список будет содержать две ячейки-болванки, содержащие null. Эти две ячейки представляют собой
    минимально допустимый вариант списка и позволяют далее в коде не заботиться о возможном отсутствии
    ячеек в списке. Все методы реализованы так, чтобы существование ячеек-болванок не влияло на работу
    со списком с точки зрения конечного пользователя. Запрещено хранить в данном списке null.
     */
    MyList() {
        Cell dummy1 = new Cell();
        Cell dummy2 = new Cell();
        dummy1.next = dummy2;
        dummy2.next = dummy1;
        this.current = dummy1;
    }

    /*
    Составить строковое представление данного списка. Удобно для отслеживания его состояния.
     */
    @Override
    public String toString() {
        if (this.size == 0) return "[]";
        StringBuilder string = new StringBuilder();
        string.append("[");

        for (int i = this.size; i > 0; --i) {
            string.append(current.value.toString());
            if (i != 1) string.append(", ");
            this.advance(1);
        }
        string.append("]");
        return string.toString();
    }

    /*
    Почти все методы не имеют смысла в случае, когда список пуст, поэтому проверка вынесена в
    отдальный метод, ведь во всех случаях надо бросить одно и то же исключение с тем же сообщением.
     */
    private void checkIfEmpty() throws IllegalStateException {
        if (this.size == 0) throw new IllegalStateException("Список пуст");
    }

    public V peek() throws IllegalStateException {
        this.checkIfEmpty();
        return this.current.value;
    }

    /*
    Вставить новую ячейку между текущей (this.current) и следующей (this.current.next).
    Если список был пуст, то поле this.current начинает указывать на новосозданную ячейку.
     */
    public void insert(V value) throws IllegalArgumentException {
        if (value == null)  throw new IllegalArgumentException("Хранение null недопустимо");
        Cell leftCell = this.current;
        Cell rightCell = this.current.next;

        this.current = new Cell();
        this.current.next = rightCell;
        this.current.value = value;
        leftCell.next = this.current;
        if (!(leftCell.value == null)) current = leftCell;
        size++;
    }

    /*
    Выбрать новую ячейку, относительно которой будут осуществляться операции над элементами списка.
    Перешагивает через ячейки-болванки, не считая их.
     */
    public void advance(int steps) throws IllegalArgumentException, IllegalStateException {
        this.checkIfEmpty();
        if (this.size == 1)  return;
        if (steps <= 0) {
            throw new IllegalArgumentException("Число шагов должно быть больше нуля");
        }
        steps = steps % this.size;

        for (int i = 0; i != steps; ++i) {
            this.current = this.current.next;
            if (current.value == null)  i--;
        }
    }

    /*
    Вернуть значение текущей ячейки и сделать текущей следующую. Если список пуст, то вернуть null.
    Именно поэтому используется Optional.
     */
    public V next() throws IllegalStateException {
        this.checkIfEmpty();
        V valueToReturn = this.current.value;

        this.advance(1);
        return valueToReturn;
    }

    /*
    Удалить текущую ячейку (current) и сделать текущей предыдущую относительно нее.
     */
    public V remove() throws IllegalStateException {
        this.checkIfEmpty();
        V valueToReturn = this.current.value;
        Cell rightCell = this.current.next;

        this.advance(this.size -1);
        this.current.next = rightCell;
        size--;
        return valueToReturn;
    }
}
