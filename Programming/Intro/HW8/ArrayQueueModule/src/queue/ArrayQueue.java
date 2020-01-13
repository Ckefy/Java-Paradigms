package queue;

import java.util.ArrayList;

public class ArrayQueue {
    private Object[] elements = new Object[15];
    private int size = 0;
    private int start = 0; //указатель на начало, где уже есть элемент
    private int end = 0; //куда в следующий раз вставаить, указатель за конец

    //Inv: (n >= 0) && elements[i] != null for i = 0..n-1;

    private int moveForward(int ind) { //передвинуть end
        return (ind + 1) % elements.length;
    }
    //Pre:ind >= 0 && ind < elements.length && elements.length != 0;
    //Post: ind` = (ind + 1) % elements.length;

    private int moveBack(int ind) { //передвинуть start
        return ind == 0 ? elements.length - 1 : ind - 1;
    }
    //Pre:x >= 0 && x < elements.length && elements.length != 0;
    //Post: (ind` = elements.length - 1 && ind = 0) || (ind` == ind - 1 && ind > 0)

    private Object[] getArray(int nowsz) {
        Object[] help = new Object[nowsz];
        int j = 0;
        for (int i = start; i != end; i = moveForward(i)) {
            help[j++] = elements[i];
        }
        return help;
    }
    //Pre: nowz >=0 && elements.length > 0
    //Post: help[i] = elements[i] for i = 0..n-1;

    public Object[] toArray() {
        return getArray(size);
    }
    //Pre: nowz >=0 && elements.length > 0
    //Post: help[i] = elements[i] for i = 0..n-1;

    private void ensureCapacity() {
        if (elements.length > size) {
            return;
        }
        elements = getArray(2 * size);
        start = 0;
        end = size - 1;
    }
    //Pre: size >= 0
    //Post: (size < elements`.length && elements`[i] = elements[i] for i = 0..n - 1

    public void enqueue(Object x) { //в конец вставить
        assert x != null;
        size++;
        ensureCapacity();
        elements[end] = x;
        end = moveForward(end);
    }
    //Pre: x != null
    //Post: n` = n + 1 && elements`[i] = elements[i] for i = 0..n - 1
    //elements`[n] = x

    public Object element() {
        assert size > 0;
        return elements[start];
    }
    //Pre: n > 0
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n-1 && ans = elements[start]

    public Object dequeue() {
        assert size > 0;
        Object need = element();
        size--;
        start = moveForward(start);
        return need;
    }
    //Pre: n > 0
    //Post: n` == n - 1 && elements`[i - 1] == elements[i] for i = 1..n-1 && ans = elements[start]

    public int size() {
        return size;
    }
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = n;

    public boolean isEmpty() {
        return size == 0;
    }
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = (n = 0);

    public void clear() {
        size = 0;
        elements = new Object[25];
        start = 0;
        end = 0;
    }
    //Pre: always true
    //Post: n == 0;

    public void push(Object x) {
        assert x != null;
        size++;
        ensureCapacity();
        start = moveBack(start);
        elements[start] = x;
    }
    //Pre: x != null
    //Post: n` == n + 1 && elements`[i + 1] = elements[i] for i = 0..n && elements`[start] = x

    public Object peek() {
        assert size > 0;
        return elements[moveBack(end)];
    }
    //Pre: n > 0
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = elements[end - 1]

    public Object remove() {
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
