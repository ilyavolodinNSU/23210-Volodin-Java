package factory.domain.suppliers;

import factory.domain.entities.parts.Accessory;
import factory.domain.storage.Storage;

public final class AccessorySupplier extends Supplier<Accessory> {
    public AccessorySupplier(Storage<Accessory> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Accessory createPart(int id) {
        return new Accessory(id);
    }
}
