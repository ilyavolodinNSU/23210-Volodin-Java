package factory.core.services;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;

public class CarServices {
    private final Repository<Car> repository;
    //private final IdGenerator idGen;

    public CarServices(Repository<Car> repository) {
        this.repository = repository;
        //this.idGen = new IdGenerator();
    }

    public void store(Car car) {
        repository.push(car);
    }

    public Car retrieve() {
        Car entity = repository.pop();
        return entity;
    }

    public Car assembleCar(Motor motor, Body body, Accessory accessory, int id) {
        return new Car(body, motor, accessory, id);
    }

    public void saleCar(Car car) {
        car = null;
    }
}
