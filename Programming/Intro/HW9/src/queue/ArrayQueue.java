package queue;

import java.util.ArrayList;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[15];
    private int start = 0;
    private int end = 0;

    private int moveForward(int ind) { //передвинуть end
        return (ind + 1) % elements.length;
    }

    private Object[] getArray(int nowsz) {
        Object[] help = new Object[nowsz];
        int j = 0;
        for (int i = start; i != end; i = moveForward(i)) {
            help[j++] = elements[i];
        }
        return help;
    }

    private void ensureCapacity() {
        if (elements.length > size) {
            return;
        }
        elements = getArray(2 * size);
        start = 0;
        end = size - 1;
    }

    protected void enqueueImpl(Object x) {
        ensureCapacity();
        elements[end] = x;
        end = moveForward(end);
    }

    protected Object elementImpl() {
        return elements[start];
    }

    protected void dequeueImpl() {
        start = moveForward(start);
    }

    protected void clearImpl() {
        elements = new Object[25];
        start = 0;
        end = 0;
    }

    protected ArrayQueue copyQueue(){
        ArrayQueue answer = new ArrayQueue();
        Object[] temp = toArray();
        for (int i = 0; i < size; i++){
            answer.enqueue(temp[i]);
        }
        return answer;
    }

    public static void main(String[] args) {

    }
}
