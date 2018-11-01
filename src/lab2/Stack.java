package lab2;

import java.util.Arrays;


class Stack {

    private int[] stack = new int[10];
    private int size = stack.length;
    private int top = -1;


    Stack() {}

    Stack(int size) throws IllegalArgumentException {
        if (size <= 0)
            throw new IllegalArgumentException("Недопустимый размер стека");
        stack = new int[size];
        this.size = size;
        }

    private void enlargeStack() {
        size *= 2;
        stack = Arrays.copyOf(stack, size);
        }

    boolean isEmpty() { return top == -1; }

    int[] getStack() { return Arrays.copyOf(stack, top +1); }

    int peek() throws IllegalStateException {
        if (this.isEmpty())
            throw new IllegalStateException("Невозможно просмотреть элемент: стек пуст");
        return stack[top];
        }

    int pop() throws IllegalStateException {
        if (this.isEmpty())
            throw new IllegalStateException("Невозможно извлечь элемент: стек пуст");
        return stack[top--];
        }

    void push(int value) {
        boolean stackIsFull = (top +1 == size);
        if (stackIsFull) enlargeStack();
        stack[++top] = value;
        }

    int depth(int value) {
        int counter = top;
        while (counter >= 0  &&  stack[counter] != value)  counter--;
        return (counter == -1) ? counter : top - counter;
    }   }
