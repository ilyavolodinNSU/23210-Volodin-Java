package factory.infrastructure.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import factory.core.entities.parts.Motor;
import factory.infrastructure.db.entities.MotorData;

@Mapper
public interface MotorMapper {
    MotorMapper INSTANCE = Mappers.getMapper(MotorMapper.class);

    MotorData toDto(Motor motor);
    Motor toEntity(MotorData dto);
}
