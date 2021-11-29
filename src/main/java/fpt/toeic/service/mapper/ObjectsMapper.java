package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.ObjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Objects} and its DTO {@link ObjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ObjectsMapper extends EntityMapper<ObjectsDTO, Objects> {


    @Mapping(target = "dsRoleObjects", ignore = true)
    @Mapping(target = "removeDsRoleObject", ignore = true)
    Objects toEntity(ObjectsDTO objectsDTO);

    default Objects fromId(Long id) {
        if (id == null) {
            return null;
        }
        Objects objects = new Objects();
        objects.setId(id);
        return objects;
    }
}
