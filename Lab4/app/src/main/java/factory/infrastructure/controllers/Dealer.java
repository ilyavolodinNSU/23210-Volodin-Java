package factory.infrastructure.controllers;

import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.entities.Car;
import factory.core.services.CarServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.repository.CarRepoController;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class Dealer implements Runnable {
    private final CarServices carServices;
    private final CarRepoController repoController;
    private final FactoryState factoryState;

    // берет машину без репозитория, продает

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(factoryState.getDealersDelay().get() * 1000);

                repoController.signal();

                Car car = carServices.retrieve();
    
                factoryState.setActiveDealers(true);
                Thread.sleep(1000);

                carServices.saleCar(car);
                factoryState.getSoldCarsCounter().incrementAndGet();

                factoryState.setActiveDealers(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}