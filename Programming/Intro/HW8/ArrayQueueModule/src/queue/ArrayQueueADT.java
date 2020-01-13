package queue;

import java.util.ArrayList;

public class ArrayQueueADT {
    private Object[] elements = new Object[15];
    private int size = 0;
    private int start = 0; //указатель на начало, где уже есть элемент
    private int end = 0; //куда в следующий раз вставаить, указатель за конец

    //Inv: (n >= 0) && elements[i] != null for i = 0..n-1;

    private static int moveForward(ArrayQueueADT example, int ind) { //передвинуть end
        return (ind + 1) % example.elements.length;
    }
    //Pre:ind >= 0 && ind < elements.length && elements.length != 0 && example != null;
    //Post: ind` = (ind + 1) % elements.length;

    private static int moveBack(ArrayQueueADT example, int ind) { //передвинуть start
        return ind == 0 ? example.elements.length - 1 : ind - 1;
    }
    //Pre:x >= 0 && x < elements.length && elements.length != 0 && example != null;
    //Post: (ind` = elements.length - 1 && ind = 0) || (ind` == ind - 1 && ind > 0)

    private static Object[] getArray(ArrayQueueADT example, int nowsz) {
        Object[] help = new Object[nowsz];
        int j = 0;
        for (int i = example.start; i != example.end; i = moveForward(example, i)) {
            help[j++] = example.elements[i];
        }
        return help;
    }
    //Pre: nowz >=0 && elements.length > 0 && example != null
    //Post: help[i] = elements[i] for i = 0..n-1;

    public static Object[] toArray(ArrayQueueADT example) {
        return getArray(example, example.size);
    }
    //Pre: nowz >=0 && elements.length > 0 && example != null
    //Post: help[i] = elements[i] for i = 0..n-1;

    private static void ensureCapacity(ArrayQueueADT example) {
        if (example.elements.length > example.size) {
            return;
        }
        example.elements = getArray(example, 2 * example.size);
        example.start = 0;
        example.end = example.size - 1;
    }
    //Pre: size >= 0 && example != null;
    //Post: (size < elements`.length && elements`[i] = elements[i] for i = 0..n - 1

    public static void enqueue(ArrayQueueADT example, Object x) { //в конец вставить
        assert x != null;
        example.size++;
        ensureCapacity(example);
        example.elements[example.end] = x;
        example.end = moveForward(example, example.end);
    }
    //Pre: x != null && example != null;
    //Post: n` = n + 1 && elements`[i] = elements[i] for i = 0..n - 1
    //elements`[n] = x

    public static Object element(ArrayQueueADT example) {
        assert example.size > 0;
        return example.elements[example.start];
    }
    //Pre: n > 0 && example != null;
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n-1 && ans = elements[start]

    public static Object dequeue(ArrayQueueADT example) {
        assert example.size > 0;
        Object need = element(example);
        example.size--;
        example.start = moveForward(example, example.start);
        return need;
    }
    //Pre: n > 0 && example != null;
    //Post: n` == n - 1 && elements`[i - 1] == elements[i] for i = 1..n-1 && ans = elements[start]

    public static int size(ArrayQueueADT example) {
        return example.size;
    }
    //Pre: example != null;
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = n;

    public static boolean isEmpty(ArrayQueueADT example) {
        return example.size == 0;
    }
    //Pre: example != null;
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = (n = 0);

    public static void clear(ArrayQueueADT example) {
        example.size = 0;
        example.elements = new Object[25];
        example.start = 0;
        example.end = 0;
    }
    //Pre: example != null;
    //Post: n == 0;

    public static void push(ArrayQueueADT example, Object x) {
        assert x != null;
        example.size++;
        ensureCapacity(example);
        example.start = moveBack(example, example.start);
        example.elements[example.start] = x;
    }
    //Pre: x != null && example != null;
    //Post: n` == n + 1 && elements`[i + 1] = elements[i] for i = 0..n && elements`[start] = x

    public static Object peek(ArrayQueueADT example) {
        assert example.size > 0;
        return example.elements[moveBack(example, example.end)];
    }
    //Pre: n > 0 && example != null;
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = elements[end - 1]

    public static Object remove(ArrayQueueADT example) {
        assert example.size > 0;
        Object need = peek(example);
        example.size--;
        example.end = moveBack(example, example.end);
        return need;
    }
    //Pre: n > 0 && example != null;
    //Post: n` = n - 1 && elements`[i] = elements[i] for i = 0..n - 2 && ans = elements[end - 1]

    public static void main(String[] args) {

    }
}
