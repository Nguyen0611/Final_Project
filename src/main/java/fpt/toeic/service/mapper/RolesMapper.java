package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.RolesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Roles} and its DTO {@link RolesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolesMapper extends EntityMapper<RolesDTO, Roles> {


    @Mapping(target = "dsUsers", ignore = true)
    @Mapping(target = "removeDsUsers", ignore = true)
    @Mapping(target = "dsRoleObjects", ignore = true)
    @Mapping(target = "removeDsRoleObject", ignore = true)
    Roles toEntity(RolesDTO rolesDTO);

    default Roles fromId(Long id) {
        if (id == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setId(id);
        return roles;
    }
}
