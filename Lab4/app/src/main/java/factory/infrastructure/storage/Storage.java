package factory.infrastructure.storage;

import java.util.*;

public class Storage<T> {
    private Queue<T> queue;
    private int maxSize;

    public Storage(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public int getSize() {
        return this.queue.size();
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public boolean isFull() {
        return queue.size() >= maxSize;
    }

    public void printSize() {
        System.out.println("size: " + queue.size());
    }

    public void push(T entity) {
        if (queue.size() < maxSize) this.queue.add(entity);
    }

    public T pop() {
        return this.queue.poll();
    }
}
