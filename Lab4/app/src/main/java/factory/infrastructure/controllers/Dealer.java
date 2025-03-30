package factory.infrastructure.controllers;

import factory.core.services.CarServices;

public class Dealer implements Runnable {
    private final CarServices carServices;

    public Dealer(CarServices carServices) {
        this.carServices = carServices;
    }

    // берет машину без репозитория, продает

    @Override
    public void run() {
        carServices.saleCar(carServices.retrieve());
    }
}