package factory.infrastructure.db.repositories;

import factory.core.entities.parts.Motor;
import factory.core.repository.IMotorRepository;
import factory.infrastructure.storage.Storage;

public class MotorRepositoryStorage implements IMotorRepository {
    Storage<Motor> storage = new Storage<>(5); // вместо этого может быть SQL etc...

    @Override
    public void push(Motor car) {
        storage.push(car);
    }

    @Override
    public Motor pop() {
        return storage.pop();
    }
}