package fpt.toeic.service.mapper;

import fpt.toeic.domain.Roles;
import fpt.toeic.domain.Users;
import fpt.toeic.service.dto.UsersDTO;
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
public class UsersMapperImpl implements UsersMapper {

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public List<Users> toEntity(List<UsersDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Users> list = new ArrayList<Users>( dtoList.size() );
        for ( UsersDTO usersDTO : dtoList ) {
            list.add( toEntity( usersDTO ) );
        }

        return list;
    }

    @Override
    public List<UsersDTO> toDto(List<Users> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UsersDTO> list = new ArrayList<UsersDTO>( entityList.size() );
        for ( Users users : entityList ) {
            list.add( toDto( users ) );
        }

        return list;
    }

    @Override
    public UsersDTO toDto(Users users) {
        if ( users == null ) {
            return null;
        }

        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setRolesId( usersRolesId( users ) );
        usersDTO.setId( users.getId() );
        usersDTO.setName( users.getName() );
        usersDTO.setFullName( users.getFullName() );
        usersDTO.setPass( users.getPass() );
        usersDTO.setPathUrl( users.getPathUrl() );
        usersDTO.setDateOfBirth( users.getDateOfBirth() );
        usersDTO.setMail( users.getMail() );
        usersDTO.setPhone( users.getPhone() );
        usersDTO.setStatus( users.getStatus() );
        usersDTO.setResetKey( users.getResetKey() );
        usersDTO.setResetDate( users.getResetDate() );
        usersDTO.setCreator( users.getCreator() );
        usersDTO.setCreationTime( users.getCreationTime() );

        return usersDTO;
    }

    @Override
    public Users toEntity(UsersDTO usersDTO) {
        if ( usersDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setRoles( rolesMapper.fromId( usersDTO.getRolesId() ) );
        users.setId( usersDTO.getId() );
        users.setName( usersDTO.getName() );
        users.setFullName( usersDTO.getFullName() );
        users.setPass( usersDTO.getPass() );
        users.setPathUrl( usersDTO.getPathUrl() );
        users.setDateOfBirth( usersDTO.getDateOfBirth() );
        users.setMail( usersDTO.getMail() );
        users.setPhone( usersDTO.getPhone() );
        users.setStatus( usersDTO.getStatus() );
        users.setResetKey( usersDTO.getResetKey() );
        users.setResetDate( usersDTO.getResetDate() );
        users.setCreator( usersDTO.getCreator() );
        users.setCreationTime( usersDTO.getCreationTime() );

        return users;
    }

    private Long usersRolesId(Users users) {
        if ( users == null ) {
            return null;
        }
        Roles roles = users.getRoles();
        if ( roles == null ) {
            return null;
        }
        Long id = roles.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
