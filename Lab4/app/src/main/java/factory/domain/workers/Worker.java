package factory.domain.workers;

import factory.domain.entities.Car;
import factory.domain.entities.parts.Accessory;
import factory.domain.entities.parts.Body;
import factory.domain.entities.parts.Motor;
import factory.domain.storage.Storage;

public class Worker implements Runnable {
    private Storage<Body> storageBody;
    private Storage<Motor> storageMotor;
    private Storage<Accessory> storageAccessory;
    private Storage<Car> storageCar;

    public Worker(Storage<Body> storageBody, Storage<Motor> storageMotor, Storage<Accessory> storageAccessory, Storage<Car> storageCar) {
        this.storageBody = storageBody;
        this.storageMotor = storageMotor;
        this.storageAccessory = storageAccessory;
        this.storageCar = storageCar;
        System.err.println("Создан работник в потоке " + Thread.currentThread().getName());
    }

    public Car assembleCar() {
        Car newCar = new Car(storageBody.pop(), storageMotor.pop(), storageAccessory.pop());

        return newCar;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Car car = assembleCar();
            storageCar.push(car);
        }
    }
}
