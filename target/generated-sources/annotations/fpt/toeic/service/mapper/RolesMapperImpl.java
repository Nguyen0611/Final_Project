package fpt.toeic.service.mapper;

import fpt.toeic.domain.Roles;
import fpt.toeic.service.dto.RolesDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-07T21:54:40+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
@Component
public class RolesMapperImpl implements RolesMapper {

    @Override
    public RolesDTO toDto(Roles entity) {
        if ( entity == null ) {
            return null;
        }

        RolesDTO rolesDTO = new RolesDTO();

        rolesDTO.setId( entity.getId() );
        rolesDTO.setName( entity.getName() );
        rolesDTO.setCode( entity.getCode() );
        rolesDTO.setDescription( entity.getDescription() );
        rolesDTO.setStatus( entity.getStatus() );
        rolesDTO.setIsLink( entity.getIsLink() );
        rolesDTO.setUpdateTime( entity.getUpdateTime() );
        rolesDTO.setType( entity.getType() );

        return rolesDTO;
    }

    @Override
    public List<Roles> toEntity(List<RolesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Roles> list = new ArrayList<Roles>( dtoList.size() );
        for ( RolesDTO rolesDTO : dtoList ) {
            list.add( toEntity( rolesDTO ) );
        }

        return list;
    }

    @Override
    public List<RolesDTO> toDto(List<Roles> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RolesDTO> list = new ArrayList<RolesDTO>( entityList.size() );
        for ( Roles roles : entityList ) {
            list.add( toDto( roles ) );
        }

        return list;
    }

    @Override
    public Roles toEntity(RolesDTO rolesDTO) {
        if ( rolesDTO == null ) {
            return null;
        }

        Roles roles = new Roles();

        roles.setId( rolesDTO.getId() );
        roles.setName( rolesDTO.getName() );
        roles.setCode( rolesDTO.getCode() );
        roles.setDescription( rolesDTO.getDescription() );
        roles.setStatus( rolesDTO.getStatus() );
        roles.setIsLink( rolesDTO.getIsLink() );
        roles.setUpdateTime( rolesDTO.getUpdateTime() );
        roles.setType( rolesDTO.getType() );

        return roles;
    }
}
