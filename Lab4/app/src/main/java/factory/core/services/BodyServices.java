package factory.core.services;

import factory.core.entities.parts.Body;
import factory.core.repository.IBodyRepository;

public class BodyServices {
    private IBodyRepository repository;

    public BodyServices(IBodyRepository repository) {
        this.repository = repository;
    }

    public void store(Body entity) {
        repository.push(entity);
    }

    public Body retrieve() {
        Body entity = repository.pop();
        return entity;
    }

    public Body supply() {
        return new Body(0);
    }
}
