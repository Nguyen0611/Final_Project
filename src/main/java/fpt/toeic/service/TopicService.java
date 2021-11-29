package fpt.toeic.service;

import fpt.toeic.domain.Objects;
import fpt.toeic.domain.Topic;
import fpt.toeic.repository.TopicRepository;
import fpt.toeic.service.dto.ObjectsDTO;
import fpt.toeic.service.dto.TopicDTO;
import fpt.toeic.service.mapper.TopicMapper;
import fpt.toeic.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Topic}.
 */
@Service
@Transactional
public class TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save.
     * @return the persisted entity.
     */
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);// convert từ DTO sang entity
        topic = topicRepository.save(topic);// Phương thức hỗ trợ của spring
        return topicMapper.toDto(topic);
    }

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TopicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Topics");
        return topicRepository.findAll(pageable)
            .map(topicMapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<TopicDTO> search(String name, Long id_type, Long id_part_topic, int page, int page_size) {
        log.debug("Request to get all Topics");
        List<TopicDTO> lstObject = topicRepository.searchTopic( DataUtil.makeLikeParam(name), id_type, id_part_topic, page, page_size)
            .stream().map(e -> {
                TopicDTO dto = new TopicDTO();// Tạo đối tượng mới , sql spring hỗ trợ kiểu entity => khi query dữ liệu thì convert kiểu dữ liệu để map vs DB
                dto.setId(DataUtil.safeToLong(e[0]));
                dto.setCode(DataUtil.safeToString(e[1]));
                dto.setName(DataUtil.safeToString(e[2]));
                dto.setIdType(DataUtil.safeToLong(e[3]));
                dto.setNameType(DataUtil.safeToString(e[4]));
                dto.setIdPartTopic(DataUtil.safeToLong(e[5]));
                dto.setNamePartTopic(DataUtil.safeToString(e[6]));
                dto.setStCreationTime( DataUtil.safeToString( e[7] ) );
                dto.setStUpdateTime( DataUtil.safeToString( e[8] ) );
                return dto;
            }).collect(Collectors.toList());
        return lstObject;
    }
    @Transactional(readOnly = true)
    public Long total(String name, Long id_type, Long id_part_topic) {
        log.debug("Request to get all Topics");
        return topicRepository.countAllByNameAndIdTypeAndIdPartTopic(DataUtil.makeLikeParam(name), id_type, id_part_topic);
    }
    /**
     * Get one topic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Topic> findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findById(id);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TopicDTO> findTopic(Long idType, Long IdPartTopic) {
        List<Topic> list = topicRepository.findAllByIdTypeAndIdPartTopic(idType, IdPartTopic);
        List<TopicDTO> topicDTOS = new ArrayList<>();
        for (Topic objects : list){
            topicDTOS.add(topicMapper.toDto(objects));
        }
        return topicDTOS;
    }

    @Transactional(readOnly = true)
    public List<TopicDTO> getTopicByPart(Long IdPartTopic) {
        List<Topic> list = topicRepository.getTopicByIdPart(IdPartTopic);
        List<TopicDTO> topicDTOS = new ArrayList<>();
        for (Topic objects : list){
            topicDTOS.add(topicMapper.toDto(objects));
        }
        return topicDTOS;
    }
}
