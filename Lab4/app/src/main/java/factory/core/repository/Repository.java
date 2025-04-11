package factory.core.repository;

public interface Repository<T> {
    public void push(T entity);
    public T pop();
    public long size();
}
