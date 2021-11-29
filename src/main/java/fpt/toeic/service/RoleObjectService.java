package fpt.toeic.service;

import fpt.toeic.domain.RoleObject;
import fpt.toeic.repository.RoleObjectRepository;
import fpt.toeic.service.dto.RoleObjectDTO;
import fpt.toeic.service.mapper.RoleObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RoleObject}.
 */
@Service
@Transactional
public class RoleObjectService {

    private final Logger log = LoggerFactory.getLogger(RoleObjectService.class);

    private final RoleObjectRepository roleObjectRepository;

    private final RoleObjectMapper roleObjectMapper;

    public RoleObjectService(RoleObjectRepository roleObjectRepository, RoleObjectMapper roleObjectMapper) {
        this.roleObjectRepository = roleObjectRepository;
        this.roleObjectMapper = roleObjectMapper;
    }

    /**
     * Save a roleObject.
     *
     * @param roleObjectDTO the entity to save.
     * @return the persisted entity.
     */
    public RoleObjectDTO save(RoleObjectDTO roleObjectDTO) {
        log.debug("Request to save RoleObject : {}", roleObjectDTO);
        RoleObject roleObject = roleObjectMapper.toEntity(roleObjectDTO);
        roleObject = roleObjectRepository.save(roleObject);
        return roleObjectMapper.toDto(roleObject);
    }

    /**
     * Get all the roleObjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleObjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleObjects");
        return roleObjectRepository.findAll(pageable)
            .map(roleObjectMapper::toDto);
    }


    /**
     * Get one roleObject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RoleObjectDTO> findOne(Long id) {
        log.debug("Request to get RoleObject : {}", id);
        return roleObjectRepository.findById(id)
            .map(roleObjectMapper::toDto);
    }

    /**
     * Delete the roleObject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RoleObject : {}", id);
        roleObjectRepository.deleteById(id);
    }
}
