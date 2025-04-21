package factory.core.entities;

import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;


public class Car {
    private Body body;
    private Motor motor;
    private Accessory accessory;
    private int id;

    public Car(Body body, Motor motor, Accessory accessory, int id) {
        this.body = body;
        this.motor = motor;
        this.accessory = accessory;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Motor getMotor() {
        return this.motor;
    }

    public Body getBody() {
        return this.body;
    }

    public Accessory getAccessory() {
        return this.accessory;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println("Car ID: " + id);
        System.out.println("Motor ID: " + motor.getId());
        System.out.println("Body ID: " + body.getId());
        System.out.println("Accessory ID: " + accessory.getId());
    }
}
