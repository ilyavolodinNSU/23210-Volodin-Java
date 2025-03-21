package factory.domain.suppliers;

import factory.domain.entities.parts.Body;
import factory.domain.storage.Storage;

public class BodySupplier extends Supplier<Body> {
    public BodySupplier(Storage<Body> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Body createPart(int id) {
        return new Body(id);
    }
}
