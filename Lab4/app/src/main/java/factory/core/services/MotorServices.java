package factory.core.services;

import factory.core.entities.parts.Motor;
import factory.core.repository.IMotorRepository;

public class MotorServices {
    private IMotorRepository repository;

    public MotorServices(IMotorRepository repository) {
        this.repository = repository;
    }

    public void store(Motor entity) {
        repository.push(entity);
    }

    public Motor retrieve() {
        Motor entity = repository.pop();
        return entity;
    }

    public Motor supply() {
        return new Motor(52);
    }
}
