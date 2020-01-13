package queue;

import java.util.function.Function;
import java.util.function.Predicate;

//Inv: (n >= 0) && elements[i] != null for i = 0..n-1;

public interface Queue {
    void enqueue(Object x);
    //Pre: x != null
    //Post: n` = n + 1 && elements`[i] = elements[i] for i = 0..n - 1
    //elements`[n] = x

    Object element();
    //Pre: n > 0
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n-1 && ans = elements[start]

    Object dequeue();
    //Pre: n > 0
    //Post: n` == n - 1 && elements`[i - 1] == elements[i] for i = 1..n-1 && ans = elements[start]

    int size();
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = n;

    boolean isEmpty();
    //Pre: always true
    //Post: n` == n && elements`[i] = elements[i] for i = 0..n - 1 && ans = (n = 0);

    void clear();
    //Pre: always true
    //Post: n == 0;

    Object[] toArray();
    //Pre: nowz >=0 && elements.length > 0
    //Post: help[i] = elements[i] for i = 0..n-1;

    AbstractQueue filter(Predicate<Object> state);
    //Pre: size >= 0
    //Post: queue only with elements[i] which satisfy Predicate;

    AbstractQueue map(Function<Object, Object> state);
    //Pre: size >= 0
    //Post: queue with result of Function with argument (elements[i]);
}