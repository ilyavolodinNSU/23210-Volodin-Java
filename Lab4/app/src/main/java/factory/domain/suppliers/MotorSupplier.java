package factory.domain.suppliers;

import factory.domain.entities.parts.Motor;
import factory.domain.storage.Storage;

public class MotorSupplier extends Supplier<Motor> {
    public MotorSupplier(Storage<Motor> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Motor createPart(int id) {
        return new Motor(id);
    }
}
