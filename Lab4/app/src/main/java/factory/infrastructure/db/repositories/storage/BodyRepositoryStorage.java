package factory.infrastructure.db.repositories.storage;

import factory.core.entities.parts.Body;
import factory.core.repository.IBodyRepository;
import factory.infrastructure.storage.Storage;

public class BodyRepositoryStorage implements IBodyRepository {
    Storage<Body> storage = new Storage<>(5); // вместо этого может быть SQL etc...

    @Override
    public void push(Body car) {
        storage.push(car);
    }

    @Override
    public Body pop() {
        return storage.pop();
    }
}