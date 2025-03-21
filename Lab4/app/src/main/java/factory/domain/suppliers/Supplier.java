package factory.domain.suppliers;

import factory.domain.storage.Storage;

// supplier (parts) <-> storage (parts)

public abstract class Supplier<T> implements Runnable {
    private Storage<T> storage;
    private int counter;

    public Supplier(Storage<T> storage, int delay) {
        bindStorage(storage);
        this.counter = 0;
        System.err.println("Создан поставщик в потоке " + Thread.currentThread().getName());
    }

    protected void bindStorage(Storage<T> storage) {
        this.storage = storage;
    }

    protected abstract T createPart(int id);

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(3000);
                T part = createPart(this.counter++);
                storage.push(part);
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
