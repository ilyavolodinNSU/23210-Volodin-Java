package factory.infrastructure.db.mapper;

import factory.core.entities.parts.Motor;
import factory.infrastructure.db.entities.MotorData;

public class MotorMapper implements Mapper<Motor, MotorData> {
    public Motor toEntity(MotorData dto) {
        return new Motor(dto.getId());
    }
}
