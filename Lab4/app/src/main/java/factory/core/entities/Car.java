package factory.core.entities;

import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;

public class Car {
    private final Body body;
    private final Motor motor;
    private final Accessory accessory;
    private final int id;

    public Car(Body body, Motor motor, Accessory accessory, int id) {
        this.body = body;
        this.motor = motor;
        this.accessory = accessory;
        this.id = id;
    }

    public void print() {
        System.out.println("Car " + id);
    }
}
