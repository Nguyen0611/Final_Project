package fpt.toeic.service;

import fpt.toeic.domain.Objects;
import fpt.toeic.repository.ObjectsRepository;
import fpt.toeic.service.dto.ObjectsDTO;
import fpt.toeic.service.mapper.ObjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Objects}.
 */
@Service
@Transactional
public class ObjectsService {

    private final Logger log = LoggerFactory.getLogger(ObjectsService.class);

    private final ObjectsRepository objectsRepository;

    private final ObjectsMapper objectsMapper;

    public ObjectsService(ObjectsRepository objectsRepository, ObjectsMapper objectsMapper) {
        this.objectsRepository = objectsRepository;
        this.objectsMapper = objectsMapper;
    }

    /**
     * Save a objects.
     *
     * @param objectsDTO the entity to save.
     * @return the persisted entity.
     */
    public ObjectsDTO save(ObjectsDTO objectsDTO) {
        log.debug("Request to save Objects : {}", objectsDTO);
        Objects objects = objectsMapper.toEntity(objectsDTO);
        objects = objectsRepository.save(objects);
        return objectsMapper.toDto(objects);
    }

    /**
     * Get all the objects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ObjectsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Objects");
        return objectsRepository.findAll(pageable)
            .map(objectsMapper::toDto);
    }


    // lay ds type of category
    @Transactional(readOnly = true)
    public List<ObjectsDTO> findAllByParentIdAndStatus(Long parent) {
        log.debug("Request to get all Objects");
        List<Objects> list =  objectsRepository.findAllByParentIdAndType(parent, 1L);
        List<ObjectsDTO> objectsDTOS = new ArrayList<>();
        for (Objects objects : list){
            objectsDTOS.add(objectsMapper.toDto(objects));
        }
        return objectsDTOS;
    }

    @Transactional(readOnly = true)
    public List<ObjectsDTO> findAllByPart(Long parent) {
        log.debug("Request to get all Objects");
        List<Objects> list =  objectsRepository.findAllByParentIdAndType(parent, 1L);
        List<ObjectsDTO> objectsDTOS = new ArrayList<>();
        for (Objects objects : list){
            objectsDTOS.add(objectsMapper.toDto(objects));
        }
        return objectsDTOS;
    }

    /**
     * Get one objects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObjectsDTO> findOne(Long id) {
        log.debug("Request to get Objects : {}", id);
        return objectsRepository.findById(id)
            .map(objectsMapper::toDto);
    }

    /**
     * Delete the objects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Objects : {}", id);
        objectsRepository.deleteById(id);
    }
}
