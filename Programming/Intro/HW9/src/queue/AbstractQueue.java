package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    public int size = 0;

    public void enqueue(Object x) {
        assert x != null;
        size++;
        enqueueImpl(x);
    }

    protected abstract void enqueueImpl(Object x);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        Object answer = element();
        size--;
        dequeueImpl();
        return answer;
    }

    protected abstract void dequeueImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public Object[] toArray() {
        Object[] help = new Object[size];
        for (int i = 0; i < size(); i++) {
            help[i] = dequeue();
            enqueue(help[i]);
        }
        return help;
    }

    protected abstract AbstractQueue copyQueue();

    public AbstractQueue filter(Predicate<Object> state) { //когда now удовлетворяет чему-то, то берем
        AbstractQueue answer = copyQueue();
        for (int i = 0; i < size; i++) {
            Object now = answer.dequeue();
            if (state.test(now)) {
                answer.enqueue(now);
            }
        }
        return answer;
    }

    public AbstractQueue map(Function<Object, Object> state) { //function переводит из одного типа в другой, результат пихаем сюда
        AbstractQueue answer = copyQueue();
        for (int i = 0; i < size; i++) {
            answer.enqueue(state.apply(answer.dequeue()));
        }
        return answer;
    }
}
