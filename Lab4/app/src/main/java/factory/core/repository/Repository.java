package factory.core.repository;

import factory.core.entities.parts.Part;

public interface Repository<T> {
    public void push(T entity);
    public T pop();
}
