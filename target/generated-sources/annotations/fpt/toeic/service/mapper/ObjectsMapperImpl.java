package fpt.toeic.service.mapper;

import fpt.toeic.domain.Objects;
import fpt.toeic.service.dto.ObjectsDTO;
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
public class ObjectsMapperImpl implements ObjectsMapper {

    @Override
    public ObjectsDTO toDto(Objects entity) {
        if ( entity == null ) {
            return null;
        }

        ObjectsDTO objectsDTO = new ObjectsDTO();

        objectsDTO.setId( entity.getId() );
        objectsDTO.setParentId( entity.getParentId() );
        objectsDTO.setName( entity.getName() );
        objectsDTO.setCode( entity.getCode() );
        objectsDTO.setDescription( entity.getDescription() );
        objectsDTO.setStatus( entity.getStatus() );
        objectsDTO.setIcon( entity.getIcon() );
        objectsDTO.setPath( entity.getPath() );
        objectsDTO.setUpdateTime( entity.getUpdateTime() );
        objectsDTO.setType( entity.getType() );

        return objectsDTO;
    }

    @Override
    public List<Objects> toEntity(List<ObjectsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Objects> list = new ArrayList<Objects>( dtoList.size() );
        for ( ObjectsDTO objectsDTO : dtoList ) {
            list.add( toEntity( objectsDTO ) );
        }

        return list;
    }

    @Override
    public List<ObjectsDTO> toDto(List<Objects> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ObjectsDTO> list = new ArrayList<ObjectsDTO>( entityList.size() );
        for ( Objects objects : entityList ) {
            list.add( toDto( objects ) );
        }

        return list;
    }

    @Override
    public Objects toEntity(ObjectsDTO objectsDTO) {
        if ( objectsDTO == null ) {
            return null;
        }

        Objects objects = new Objects();

        objects.setId( objectsDTO.getId() );
        objects.setParentId( objectsDTO.getParentId() );
        objects.setName( objectsDTO.getName() );
        objects.setCode( objectsDTO.getCode() );
        objects.setDescription( objectsDTO.getDescription() );
        objects.setStatus( objectsDTO.getStatus() );
        objects.setIcon( objectsDTO.getIcon() );
        objects.setPath( objectsDTO.getPath() );
        objects.setUpdateTime( objectsDTO.getUpdateTime() );
        objects.setType( objectsDTO.getType() );

        return objects;
    }
}
