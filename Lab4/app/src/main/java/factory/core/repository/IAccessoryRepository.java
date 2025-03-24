package factory.core.repository;

import factory.core.entities.parts.Accessory;

public interface IAccessoryRepository {
    public void push(Accessory entity);
    public Accessory pop();
}
