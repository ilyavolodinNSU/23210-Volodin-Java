package factory.core.repository;

import factory.core.entities.parts.Motor;

public interface IMotorRepository {
    public void push(Motor entity);
    public Motor pop();
}
