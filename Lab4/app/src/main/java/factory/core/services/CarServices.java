package factory.core.services;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.repository.ICarRepository;

public class CarServices {
    private ICarRepository repository;

    public CarServices(ICarRepository repository) {
        this.repository = repository;
    }

    public void store(Car car) {
        repository.push(car);
    }

    public Car retrieve() {
        Car entity = repository.pop();
        return entity;
    }

    public Car assembleCar(Motor motor, Body body, Accessory accessory) {
        return new Car(body, motor, accessory, CarIdGenerator.generateId());
    }

    public void saleCar(Car car) {
        car = null;
    }
}
