package fpt.toeic.service;

import fpt.toeic.domain.SysLogs;
import fpt.toeic.repository.SysLogsRepository;
import fpt.toeic.service.dto.SysLogsDTO;
import fpt.toeic.service.mapper.SysLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysLogs}.
 */
@Service
@Transactional
public class SysLogsService {

    private final Logger log = LoggerFactory.getLogger(SysLogsService.class);

    private final SysLogsRepository sysLogsRepository;

    private final SysLogsMapper sysLogsMapper;

    public SysLogsService(SysLogsRepository sysLogsRepository, SysLogsMapper sysLogsMapper) {
        this.sysLogsRepository = sysLogsRepository;
        this.sysLogsMapper = sysLogsMapper;
    }

    /**
     * Save a sysLogs.
     *
     * @param sysLogsDTO the entity to save.
     * @return the persisted entity.
     */
    public SysLogsDTO save(SysLogsDTO sysLogsDTO) {
        log.debug("Request to save SysLogs : {}", sysLogsDTO);
        SysLogs sysLogs = sysLogsMapper.toEntity(sysLogsDTO);
        sysLogs = sysLogsRepository.save(sysLogs);
        return sysLogsMapper.toDto(sysLogs);
    }

    /**
     * Get all the sysLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysLogs");
        return sysLogsRepository.findAll(pageable)
            .map(sysLogsMapper::toDto);
    }


    /**
     * Get one sysLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysLogsDTO> findOne(Long id) {
        log.debug("Request to get SysLogs : {}", id);
        return sysLogsRepository.findById(id)
            .map(sysLogsMapper::toDto);
    }

    /**
     * Delete the sysLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysLogs : {}", id);
        sysLogsRepository.deleteById(id);
    }
}
