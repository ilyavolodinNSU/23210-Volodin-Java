package factory.infrastructure.db.mapper;

public interface Mapper<E, D> {
    // public D toDTO(E entity);
    public E toEntity(D dto);
}
