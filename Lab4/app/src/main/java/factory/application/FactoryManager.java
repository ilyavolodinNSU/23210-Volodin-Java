package factory.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import factory.domain.dealers.Dealer;
import factory.domain.entities.Car;
import factory.domain.entities.parts.Accessory;
import factory.domain.entities.parts.Body;
import factory.domain.entities.parts.Motor;
import factory.domain.storage.Storage;
import factory.domain.suppliers.AccessorySupplier;
import factory.domain.suppliers.BodySupplier;
import factory.domain.suppliers.MotorSupplier;
import factory.domain.workers.Worker;

public class FactoryManager {
    public static void main(String[] args) {
        // 1 pool
        Storage storageBody = new Storage<Body>(2);
        Storage storageMotor = new Storage<Motor>(2);
        Storage storageAccessory = new Storage<Accessory>(5);

        BodySupplier bs = new BodySupplier(storageBody, 1000);
        MotorSupplier ms = new MotorSupplier(storageMotor, 1000);

        ExecutorService executor1 = Executors.newFixedThreadPool(3);

        executor1.execute(bs);
        executor1.execute(ms);

        for (int i = 0; i < 3; i++) {
            AccessorySupplier as = new AccessorySupplier(storageAccessory, 1000);
            executor1.execute(as);
        }

        // 2 pool
        Storage storageCar = new Storage<Car>(10);
        Worker worker = new Worker(storageBody, storageMotor, storageAccessory, storageCar);

        ExecutorService executor2 = Executors.newFixedThreadPool(1);

        executor2.execute(worker);

        // 3 pool
        Dealer dealer1 = new Dealer(storageCar);

        ExecutorService executor3 = Executors.newFixedThreadPool(1);

        executor3.execute(dealer1);

        // end
        executor1.shutdown();
        executor2.shutdown();
        executor3.shutdown();
    }
}
