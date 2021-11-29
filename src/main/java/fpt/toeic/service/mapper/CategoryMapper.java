package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {TopicMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "topic.id", target = "topicId")
    CategoryDTO toDto(Category category);

    @Mapping(target = "dsQuestionAnswers", ignore = true)
    @Mapping(target = "removeDsQuestionAnswers", ignore = true)
    @Mapping(target = "dsFileUploads", ignore = true)
    @Mapping(target = "removeDsFileUpload", ignore = true)
    @Mapping(source = "topicId", target = "topic")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
