package factory.domain.dealers;

import factory.domain.entities.Car;
import factory.domain.storage.Storage;

public class Dealer implements Runnable {
    private int counter;
    private Storage<Car> storageCar;

    public Dealer(Storage<Car> storageCar) {
        this.storageCar = storageCar;
        System.out.println("Создан дилер в потоке " + Thread.currentThread().getName());
        counter = 0;
    }

    public void saleCar() {
        storageCar.pop();
        System.out.println("Продано " + ++counter + " машин");
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                saleCar();
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
