package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.RoleObjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleObject} and its DTO {@link RoleObjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {RolesMapper.class, ObjectsMapper.class})
public interface RoleObjectMapper extends EntityMapper<RoleObjectDTO, RoleObject> {

    @Mapping(source = "roles.id", target = "rolesId")
    @Mapping(source = "objects.id", target = "objectsId")
    RoleObjectDTO toDto(RoleObject roleObject);

    @Mapping(source = "rolesId", target = "roles")
    @Mapping(source = "objectsId", target = "objects")
    RoleObject toEntity(RoleObjectDTO roleObjectDTO);

    default RoleObject fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoleObject roleObject = new RoleObject();
        roleObject.setId(id);
        return roleObject;
    }
}
