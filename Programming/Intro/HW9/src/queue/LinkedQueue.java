package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    protected void enqueueImpl(Object x) {
        if (size == 1) {
            head = tail = new Node(x, null);
        } else {
            tail.next = new Node(x, null);
            tail = tail.next;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void dequeueImpl() {
        head = head.next;
        if (size == 0) {
            tail = head = null;
        }
    }

    protected void clearImpl() {
        tail = head = null;
    }

    protected LinkedQueue copyQueue() {
        LinkedQueue answer = new LinkedQueue();
        Node now = head;
        while (now != null){
            answer.enqueue(now.value);
            now = now.next;
        }
        return answer;
    }

    private class Node {
        private Object value;
        private Node next;

        private Node(Object newVal, Node newNext) {
            value = newVal;
            next = newNext;
        }
    }
}
