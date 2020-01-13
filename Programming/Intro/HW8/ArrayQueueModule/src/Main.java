import queue.*;

import java.sql.SQLOutput;
import java.util.Arrays;

public class Main {

    public static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.push(i);
        }
    }
    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.peek() + " " +
                            ArrayQueueModule.remove()
            );
            System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
        }
    }

    public static void fill1(ArrayQueueADT que) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.push(que, i);
        }
    }
    public static void dump1(ArrayQueueADT que) {
        while (!ArrayQueueADT.isEmpty(que)) {
            System.out.println(
                    ArrayQueueADT.size(que) + " " +
                            ArrayQueueADT.peek(que) + " " +
                            ArrayQueueADT.remove(que)
            );
            System.out.println(Arrays.toString(ArrayQueueADT.toArray(que)));
        }
    }

    public static void fill2(ArrayQueue que2) {
        for (int i = 0; i < 10; i++) {
            que2.push(i);
        }
    }
    public static void dump2(ArrayQueue que2) {
        while (!que2.isEmpty()) {
            System.out.println(
                    que2.size() + " " +
                            que2.peek() + " " +
                            que2.remove()
            );

            System.out.println(Arrays.toString(que2.toArray()));
        }
    }

    public static void main(String[] args) {
        fill();
        dump();
        ArrayQueueADT que = new ArrayQueueADT();
        fill1(que);
        dump1(que);
        ArrayQueue que2 = new ArrayQueue();
        fill2(que2);
        dump2(que2);
    }
}