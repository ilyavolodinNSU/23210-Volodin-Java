package factory.infrastructure.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import factory.core.entities.parts.Body;
import factory.infrastructure.db.entities.BodyData;

@Mapper
public interface BodyMapper {
    BodyMapper INSTANCE = Mappers.getMapper(BodyMapper.class);

    BodyData toDto(Body motor);
    Body toEntity(BodyData dto);
}
