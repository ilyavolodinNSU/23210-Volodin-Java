package factory.infrastructure.db.mapper;

import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.infrastructure.db.entities.CarData;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(target = "bodyId", source = "body.id")
    @Mapping(target = "motorId", source = "motor.id")
    @Mapping(target = "accessoryId", source = "accessory.id")
    CarData toDto(Car car);

    @Mapping(target = "body", source = "bodyId", qualifiedByName = "mapBody")
    @Mapping(target = "motor", source = "motorId", qualifiedByName = "mapMotor")
    @Mapping(target = "accessory", source = "accessoryId", qualifiedByName = "mapAccessory")
    Car toEntity(CarData dto);

    @Named("mapMotor")
    default Motor mapMotor(int id) {
        return new Motor(id);
    }

    @Named("mapBody")
    default Body mapBody(int id) {
        return new Body(id);
    }

    @Named("mapAccessory")
    default Accessory mapAccessory(int id) {
        return new Accessory(id);
    }
}
