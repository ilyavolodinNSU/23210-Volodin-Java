package factory.infrastructure.db.repositories.storage;

import factory.core.entities.Car;
import factory.core.repository.ICarRepository;
import factory.infrastructure.storage.Storage;

public class CarRepositoryStorage implements ICarRepository {
    Storage<Car> storage = new Storage<>(10); // вместо этого может быть SQL etc...

    @Override
    public void push(Car car) {
        storage.push(car);
    }

    @Override
    public Car pop() {
        return storage.pop();
    }
}
