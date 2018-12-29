package input;

import java.util.LinkedList;

public class Queue<E> extends LinkedList<E> {

    private Object[] queue;       // The underlying array
    private int size;             // The maximal capacity
    private int head      = 0;    // Pointer to head of queue
    private int tail      = 0;    // Pointer to tail of queue
    public boolean empty = true; // Whether the queue is empty or not


    public  Queue(int size) {
        this.queue = new Object[size];
        this.size  = size;
    }

    public  synchronized  boolean add(E elem) {
        // Check if the queue is full and throw exception
        if (head == tail && !empty) {
            return false;
        }

        // The queue has space left, enqueue the item
        queue[tail] = elem;
        tail        = (tail + 1) % size;
        empty       = false;
        return true;
    }


    public  synchronized E get()  {
        // Check if queue is empty and throw exception
        if (empty) {
           return null;
        }

        // The queue is not empty, return element
        E elem = (E) queue[head];
        head   = (head + 1) % size;
        empty  = (head == tail);
        return elem;
    }
}