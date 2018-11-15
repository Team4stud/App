package input;

import java.util.LinkedList;

public class LimitedQueue<E> extends LinkedList<E> {

    private int limit;

    public LimitedQueue(int max_size) {
        limit = max_size;
    }

    @Override
    public synchronized boolean add(E o) {
        if (size() < limit) {
            super.add(o);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean isFull() {
        return size() >= limit;
    }

    public synchronized E get() {
        if (size() > 0)
            return remove(size()-1);
        else
            return null;
    }
}
