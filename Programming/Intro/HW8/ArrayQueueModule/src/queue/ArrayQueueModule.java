package queue;

import java.util.ArrayList;

public class ArrayQueueModule {
    private static Object[] elements = new Object[15];
    private static int size = 0;
    private static int start = 0; //указатель на начало, где уже есть элемент
    private static int end = 0; //куда в следующий раз вставаить, указатель за конец

    //Inv: (n >= 0) && elements[i] != null for i = 0..n-1;

    private static int moveForward(int ind) { //передвинуть end
        return (ind + 1) % elements.length;
    }
    //Pre:ind >= 0 && ind < elements.length && elements.length != 0;
    //Post: ind` = (ind + 1) % elements.length;

    private static int moveBack(int ind) { //передвинуть start
        return ind == 0 ? elements.length - 1 : ind - 1;
    }
    //Pre:ind >= 0 && ind < elements.length && elements.length != 0;
    //Post: (ind` = elements.length - 1 && ind = 0) || (ind` == ind - 1 && ind > 0)

    private static Object[] getArray(int nowsz) {
        Object[] help = new Object[nowsz];
        int j = 0;
        for (int i = start; i != end; i = moveForward(i)) {
            help[j++] = elements[i];
        }
        return help;
    }
    //Pre: nowz >=0 && elements.length > 0
    //Post: help[i] = elements[i] for i = 0..n-1;

    public static Object[] toArray() {
        return getArray(size);
    }
    //Pre: nowz >=0 && elements.length > 0
    //Post: help[i] = elements[i] for i = 0..n-1;

    private static void ensureCapacity() {
        if (elements.length > size) {
            return;
        }
        elements = getArray(2 * size);
        start = 0;
        end = size - 1;
    }
    //Pre: size >= 0
    //Post: (size < elements`.length && elements`[i] = elements[i] for i = 0..n - 1

    public static void enqueue(Object x) { //в конец вставить
        assert x != null;
        size++;
        ensureCapacity();
        elements[end] = x;
        end = moveForward(end);
    }
    //Pre: x != null
    //Post: n` = n + 1 && elements`[i] = elements[i] for i = 0..n - 1
    //elements`[n] = x

    public static Object element() {
        assert size > 0;
        return elements[start];
    }
    //Pre: n > 0
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n-1 && ans = elements[start]

    public static Object dequeue() {
        assert size > 0;
        Object need = element();
        size--;
        start = moveForward(start);
        return need;
    }
    //Pre: n > 0
    //Post: n` == n - 1 && elements`[i - 1] == elements[i] for i = 1..n-1 && ans = elements[start]

    public static int size() {
        return size;
    }
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = n;

    public static boolean isEmpty() {
        return size == 0;
    }
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = (n = 0);

    public static void clear() {
        size = 0;
        elements = new Object[25];
        start = 0;
        end = 0;
    }
    //Pre: always true
    //Post: n == 0;

    public static void push(Object x) {
        assert x != null;
        size++;
        ensureCapacity();
        start = moveBack(start);
        elements[start] = x;
    }
    //Pre: x != null
    //Post: n` == n + 1 && elements`[i + 1] = elements[i] for i = 0..n && elements`[start] = x

    public static Object peek() {
        assert size > 0;
        return elements[moveBack(end)];
    }
    //Pre: n > 0
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = elements[end - 1]

    public static Object remove() {
        assert size > 0;
        Object need = peek();
        size--;
        end = moveBack(end);
        return need;
    }
    //Pre: n > 0
    //Post: n` = n - 1 && elements`[i] = elements[i] for i = 0..n - 2 && ans = elements[end - 1]

    public static void main(String[] args) {

    }
}
