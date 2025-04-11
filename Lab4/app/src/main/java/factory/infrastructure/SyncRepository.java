package factory.infrastructure;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import factory.core.repository.Repository;

public class SyncRepository<T> implements Repository<T> {
    private final Repository<T> repository;
    //private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public SyncRepository(Repository<T> repository) {
        this.repository = repository;
    }

    @Override
    public long size() {
        lock.lock();
        long size = repository.size();
        lock.unlock();

        return size;
    }

    @Override
    public void push(T item) {
        lock.lock();
        try {
            while (repository.size() >= 10) {
                notFull.await();
            }
            repository.push(item);
            notEmpty.signal();
        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T pop() {
        T item = null;
        lock.lock();
        try {
            while (repository.size() == 0) {
                notEmpty.await();
            }
            item = repository.pop();
            notFull.signalAll();
        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            lock.unlock();
        }

        return item;
    }

    public void notFullSignalAll() {
        lock.lock();
        notFull.signalAll();
        lock.unlock();
    }
}
