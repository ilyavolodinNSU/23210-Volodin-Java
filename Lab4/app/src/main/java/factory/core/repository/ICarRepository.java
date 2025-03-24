package factory.core.repository;

import factory.core.entities.Car;

public interface ICarRepository {
    public void push(Car entity);
    public Car pop();
}
