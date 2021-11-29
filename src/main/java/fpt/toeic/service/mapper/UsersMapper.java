package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.UsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Users} and its DTO {@link UsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {RolesMapper.class})
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {

    @Mapping(source = "roles.id", target = "rolesId")
    UsersDTO toDto(Users users);

    @Mapping(source = "rolesId", target = "roles")
    Users toEntity(UsersDTO usersDTO);

    default Users fromId(Long id) {
        if (id == null) {
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}
