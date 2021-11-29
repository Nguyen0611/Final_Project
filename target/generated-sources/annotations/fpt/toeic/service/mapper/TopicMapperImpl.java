package fpt.toeic.service.mapper;

import fpt.toeic.domain.Topic;
import fpt.toeic.service.dto.TopicDTO;
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
public class TopicMapperImpl implements TopicMapper {

    @Override
    public TopicDTO toDto(Topic entity) {
        if ( entity == null ) {
            return null;
        }

        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setId( entity.getId() );
        topicDTO.setName( entity.getName() );
        topicDTO.setCode( entity.getCode() );
        topicDTO.setIdType( entity.getIdType() );
        topicDTO.setIdPartTopic( entity.getIdPartTopic() );
        topicDTO.setUpdateTime( entity.getUpdateTime() );
        topicDTO.setCreationTime( entity.getCreationTime() );

        return topicDTO;
    }

    @Override
    public List<Topic> toEntity(List<TopicDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Topic> list = new ArrayList<Topic>( dtoList.size() );
        for ( TopicDTO topicDTO : dtoList ) {
            list.add( toEntity( topicDTO ) );
        }

        return list;
    }

    @Override
    public List<TopicDTO> toDto(List<Topic> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TopicDTO> list = new ArrayList<TopicDTO>( entityList.size() );
        for ( Topic topic : entityList ) {
            list.add( toDto( topic ) );
        }

        return list;
    }

    @Override
    public Topic toEntity(TopicDTO topicDTO) {
        if ( topicDTO == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setId( topicDTO.getId() );
        topic.setName( topicDTO.getName() );
        topic.setCode( topicDTO.getCode() );
        topic.setIdType( topicDTO.getIdType() );
        topic.setIdPartTopic( topicDTO.getIdPartTopic() );
        topic.setUpdateTime( topicDTO.getUpdateTime() );
        topic.setCreationTime( topicDTO.getCreationTime() );

        return topic;
    }
}
