package fpt.toeic.service.mapper;

import fpt.toeic.domain.Objects;
import fpt.toeic.domain.RoleObject;
import fpt.toeic.domain.Roles;
import fpt.toeic.service.dto.RoleObjectDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-07T21:54:40+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
@Component
public class RoleObjectMapperImpl implements RoleObjectMapper {

    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private ObjectsMapper objectsMapper;

    @Override
    public List<RoleObject> toEntity(List<RoleObjectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RoleObject> list = new ArrayList<RoleObject>( dtoList.size() );
        for ( RoleObjectDTO roleObjectDTO : dtoList ) {
            list.add( toEntity( roleObjectDTO ) );
        }

        return list;
    }

    @Override
    public List<RoleObjectDTO> toDto(List<RoleObject> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleObjectDTO> list = new ArrayList<RoleObjectDTO>( entityList.size() );
        for ( RoleObject roleObject : entityList ) {
            list.add( toDto( roleObject ) );
        }

        return list;
    }

    @Override
    public RoleObjectDTO toDto(RoleObject roleObject) {
        if ( roleObject == null ) {
            return null;
        }

        RoleObjectDTO roleObjectDTO = new RoleObjectDTO();

        roleObjectDTO.setRolesId( roleObjectRolesId( roleObject ) );
        roleObjectDTO.setObjectsId( roleObjectObjectsId( roleObject ) );
        roleObjectDTO.setId( roleObject.getId() );
        roleObjectDTO.setUpdateTime( roleObject.getUpdateTime() );

        return roleObjectDTO;
    }

    @Override
    public RoleObject toEntity(RoleObjectDTO roleObjectDTO) {
        if ( roleObjectDTO == null ) {
            return null;
        }

        RoleObject roleObject = new RoleObject();

        roleObject.setRoles( rolesMapper.fromId( roleObjectDTO.getRolesId() ) );
        roleObject.setObjects( objectsMapper.fromId( roleObjectDTO.getObjectsId() ) );
        roleObject.setId( roleObjectDTO.getId() );
        roleObject.setUpdateTime( roleObjectDTO.getUpdateTime() );

        return roleObject;
    }

    private Long roleObjectRolesId(RoleObject roleObject) {
        if ( roleObject == null ) {
            return null;
        }
        Roles roles = roleObject.getRoles();
        if ( roles == null ) {
            return null;
        }
        Long id = roles.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long roleObjectObjectsId(RoleObject roleObject) {
        if ( roleObject == null ) {
            return null;
        }
        Objects objects = roleObject.getObjects();
        if ( objects == null ) {
            return null;
        }
        Long id = objects.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
