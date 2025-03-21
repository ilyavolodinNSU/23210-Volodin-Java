package factory.domain.storage;

import java.util.*;

public class Storage<T> {
    private Queue<T> queue;
    private int maxSize;

    public Storage(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized boolean isFull() {
        return queue.size() >= maxSize;
    }

    public synchronized void print() {
        System.out.println("size: " + queue.size());
    }

    public synchronized void push(T part) {
        if (isFull()) {
            try {
                System.out.println(Thread.currentThread().getName() + " склад переполнен " + part.toString());
                wait();
            } catch (InterruptedException e) {
            }
        }

        this.queue.add(part);
        notify();
        System.out.println(Thread.currentThread().getName() + " добавил элемент " + part.toString() + " элементов на складе: " + queue.size());
    }

    public synchronized T pop() {
        if (queue.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + " склад пуст");
                wait();
            } catch (InterruptedException e) {
            }
        } 

        System.out.println(Thread.currentThread().getName() + " взял элемент");
        notify();
        return this.queue.poll();
    }
}
