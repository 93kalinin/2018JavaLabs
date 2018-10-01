package prac2;

import java.util.Optional;

/**
 * Реализует односвязный циклический список.
 * @param <V> обозначает тип содержимого ячейки списка.
 * @version 0.1 30.09.2018.
 */
public class MyList<V> {

    private class Cell {
        Cell next;
        V value;
    }


    private Cell current;
    private int size;    //TODO: внимательно с этой хренью

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

    public int getSize() { return size; }

    /*
    Вставить новую ячейку между текущей (this.current) и следующей (this.current.next).
    Если список был пуст, то поле this.current начинает указывать на новосозданную ячейку.
     */
    public void insert(V value) throws IllegalArgumentException {
        if (value == null)  throw new IllegalArgumentException("Хранение null недопустимо");
        Cell cell1 = this.current;
        Cell cell2 = this.current.next;

        this.current = new Cell();
        this.current.next = cell2;
        this.current.value = value;
        cell1.next = this.current;
        if (!(cell1.value == null)) current = cell1;
        size++;
    }

    /*
    Выбрать новую ячейку, относительно которой будут осуществляться операции над элементами списка.
    Перешагивает через ячейки-болванки, не считая их.
     */
    public void advance(int steps) throws IllegalArgumentException, IllegalStateException {
        if (this.size == 0)  throw new IllegalStateException("Список пуст");
        if (this.size == 1)  return;
        if (steps <= 0) {
            throw new IllegalArgumentException("Число шагов должно быть больше нуля");
        }
        steps = steps % this.size;

        for (int i = 0; i != steps; ++i) {    //TODO: продумать.затестить
            this.current = this.current.next;
            if (current.value == null)  i--;
        }
    }

    /*
    Возвращает значение текущей ячейки и делает текущей следующую. Если список пуст, то возвращает
    null. Именно поэтому используется Optional.
    TODO: нарушает инвариант, выставляя current на болванки
     */
    public Optional<V> next() {
        Optional<V> result = Optional.of(this.current.value);
        this.current = this.current.next;
        return result;
    }
}
