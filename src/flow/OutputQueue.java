package flow;

import input.LimitedQueue;

public class OutputQueue {
    LimitedQueue<Frame> queue;
    private int limit = 100;

    public OutputQueue() {
        this.queue = new LimitedQueue<Frame>(limit);
    }

    public synchronized void add(Frame frame) {
        queue.add(frame);
    }

    public synchronized Frame get() {
        if(!queue.isEmpty())
            return queue.get();
        else
            return null;
    }


}
