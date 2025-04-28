package factory.core.services;

import java.util.function.Function;

import factory.core.entities.parts.Part;
import factory.core.repository.Repository;

public class PartServices<PartType extends Part> {
    private final Repository<PartType> repository;
    private final Function<Integer, PartType> factory;
    //private final IdGenerator idGen;

    public PartServices(Repository<PartType> repository, Function<Integer, PartType> factory) {
        this.repository = repository;
        this.factory = factory;
        //this.idGen = new IdGenerator();
    }

    public void store(PartType entity) {
        repository.push(entity);
    }

    public PartType retrieve() {
        PartType entity = repository.pop();
        return entity;
    }

    public PartType supply(int id) {
        return factory.apply(id);
    }
}
