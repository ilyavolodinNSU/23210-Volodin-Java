package factory.core.services;

import factory.core.entities.parts.Accessory;
import factory.core.repository.IAccessoryRepository;

public class AccessoryServices {
    private IAccessoryRepository repository;

    public AccessoryServices(IAccessoryRepository repository) {
        this.repository = repository;
    }

    public void store(Accessory entity) {
        repository.push(entity);
    }

    public Accessory retrieve() {
        Accessory entity = repository.pop();
        return entity;
    }

    public Accessory supply() {
        return new Accessory(0);
    }
}
