package factory.infrastructure.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import factory.core.entities.parts.Accessory;
import factory.infrastructure.db.entities.AccessoryData;

@Mapper
public interface AccessoryMapper {
    AccessoryMapper INSTANCE = Mappers.getMapper(AccessoryMapper.class);

    AccessoryData toDto(Accessory accessory);
    Accessory toEntity(AccessoryData dto);
}
