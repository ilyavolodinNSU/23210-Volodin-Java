package factory.core.repository;

import factory.core.entities.parts.Body;

public interface IBodyRepository {
    public void push(Body body);
    public Body pop();
}
