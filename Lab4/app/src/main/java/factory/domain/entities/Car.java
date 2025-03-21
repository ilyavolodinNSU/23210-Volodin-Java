package factory.domain.entities;

import factory.domain.entities.parts.Accessory;
import factory.domain.entities.parts.Body;
import factory.domain.entities.parts.Motor;

public class Car {
    private final Body body;
    private final Motor motor;
    private final Accessory accessory;

    public Car(Body body, Motor motor, Accessory accessory) {
        this.body = body;
        this.motor = motor;
        this.accessory = accessory;
    }
}
