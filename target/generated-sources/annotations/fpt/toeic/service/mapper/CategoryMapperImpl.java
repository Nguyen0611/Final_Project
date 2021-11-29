package fpt.toeic.service.mapper;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.Topic;
import fpt.toeic.service.dto.CategoryDTO;
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
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<Category> toEntity(List<CategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( dtoList.size() );
        for ( CategoryDTO categoryDTO : dtoList ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> toDto(List<Category> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entityList.size() );
        for ( Category category : entityList ) {
            list.add( toDto( category ) );
        }

        return list;
    }

    @Override
    public CategoryDTO toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setTopicId( categoryTopicId( category ) );
        categoryDTO.setId( category.getId() );
        categoryDTO.setCode( category.getCode() );
        categoryDTO.setCategoryName( category.getCategoryName() );
        categoryDTO.setStatus( category.getStatus() );
        categoryDTO.setUpdateTime( category.getUpdateTime() );
        categoryDTO.setCreationTime( category.getCreationTime() );

        return categoryDTO;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setTopic( topicMapper.fromId( categoryDTO.getTopicId() ) );
        category.setId( categoryDTO.getId() );
        category.setCode( categoryDTO.getCode() );
        category.setCategoryName( categoryDTO.getCategoryName() );
        category.setStatus( categoryDTO.getStatus() );
        category.setUpdateTime( categoryDTO.getUpdateTime() );
        category.setCreationTime( categoryDTO.getCreationTime() );

        return category;
    }

    private Long categoryTopicId(Category category) {
        if ( category == null ) {
            return null;
        }
        Topic topic = category.getTopic();
        if ( topic == null ) {
            return null;
        }
        Long id = topic.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
