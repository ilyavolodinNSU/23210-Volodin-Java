package factory.infrastructure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FactoryStateMapper {

    FactoryStateMapper INSTANCE = Mappers.getMapper(FactoryStateMapper.class);

    @Mapping(source = "activeTasksCounter", target = "activeTasksCounter", qualifiedByName = "atomicToInt")
    @Mapping(source = "motorStorageSize", target = "motorStorageSize", qualifiedByName = "atomicToLong")
    @Mapping(source = "bodyStorageSize", target = "bodyStorageSize", qualifiedByName = "atomicToLong")
    @Mapping(source = "accessoryStorageSize", target = "accessoryStorageSize", qualifiedByName = "atomicToLong")
    @Mapping(source = "carStorageSize", target = "carStorageSize", qualifiedByName = "atomicToLong")
    @Mapping(source = "soldCarsCounter", target = "soldCarsCounter", qualifiedByName = "atomicToInt")

    @Mapping(source = "suppMDelay", target = "suppMDelay", qualifiedByName = "atomicToInt")
    @Mapping(source = "suppBDelay", target = "suppBDelay", qualifiedByName = "atomicToInt")
    @Mapping(source = "suppsADelay", target = "suppsADelay", qualifiedByName = "atomicToInt")
    @Mapping(source = "dealersDelay", target = "dealersDelay", qualifiedByName = "atomicToInt")
    FactoryStateData toDto(FactoryState state);

    @Named("atomicToInt")
    static int atomicToInt(AtomicInteger value) {
        return value.get();
    }

    @Named("atomicToLong")
    static long atomicToLong(AtomicLong value) {
        return value.get();
    }
}
