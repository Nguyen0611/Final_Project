package fpt.toeic.service.mapper;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.FileUpload;
import fpt.toeic.service.dto.FileUploadDTO;
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
public class FileUploadMapperImpl implements FileUploadMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<FileUpload> toEntity(List<FileUploadDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FileUpload> list = new ArrayList<FileUpload>( dtoList.size() );
        for ( FileUploadDTO fileUploadDTO : dtoList ) {
            list.add( toEntity( fileUploadDTO ) );
        }

        return list;
    }

    @Override
    public List<FileUploadDTO> toDto(List<FileUpload> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FileUploadDTO> list = new ArrayList<FileUploadDTO>( entityList.size() );
        for ( FileUpload fileUpload : entityList ) {
            list.add( toDto( fileUpload ) );
        }

        return list;
    }

    @Override
    public FileUploadDTO toDto(FileUpload fileUpload) {
        if ( fileUpload == null ) {
            return null;
        }

        FileUploadDTO fileUploadDTO = new FileUploadDTO();

        fileUploadDTO.setCategoryId( fileUploadCategoryId( fileUpload ) );
        fileUploadDTO.setId( fileUpload.getId() );
        fileUploadDTO.setTypeFile( fileUpload.getTypeFile() );
        fileUploadDTO.setPath( fileUpload.getPath() );

        return fileUploadDTO;
    }

    @Override
    public FileUpload toEntity(FileUploadDTO fileUploadDTO) {
        if ( fileUploadDTO == null ) {
            return null;
        }

        FileUpload fileUpload = new FileUpload();

        fileUpload.setCategory( categoryMapper.fromId( fileUploadDTO.getCategoryId() ) );
        fileUpload.setId( fileUploadDTO.getId() );
        fileUpload.setTypeFile( fileUploadDTO.getTypeFile() );
        fileUpload.setPath( fileUploadDTO.getPath() );

        return fileUpload;
    }

    private Long fileUploadCategoryId(FileUpload fileUpload) {
        if ( fileUpload == null ) {
            return null;
        }
        Category category = fileUpload.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
