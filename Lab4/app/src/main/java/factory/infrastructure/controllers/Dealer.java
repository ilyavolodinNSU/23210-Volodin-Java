package factory.infrastructure.controllers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import factory.core.entities.Car;
import factory.core.services.CarServices;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Dealer implements Runnable {
    private final CarServices carServices;
    private final CarRepoController repoController;

    // берет машину без репозитория, продает

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("⏳ D: init");

            repoController.signal();

            Car car = carServices.retrieve();

            System.out.println(Thread.currentThread().getName() + " машина получена со склада");

            carServices.saleCar(car);

            System.out.println("D: машина продана");
        }
    }
}