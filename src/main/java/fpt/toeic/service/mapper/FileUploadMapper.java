package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.FileUploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileUpload} and its DTO {@link FileUploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface FileUploadMapper extends EntityMapper<FileUploadDTO, FileUpload> {

    @Mapping(source = "category.id", target = "categoryId")
    FileUploadDTO toDto(FileUpload fileUpload);

    @Mapping(source = "categoryId", target = "category")
    FileUpload toEntity(FileUploadDTO fileUploadDTO);

    default FileUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setId(id);
        return fileUpload;
    }
}
