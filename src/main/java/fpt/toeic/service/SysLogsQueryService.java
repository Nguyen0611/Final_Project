package fpt.toeic.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fpt.toeic.domain.SysLogs;
import fpt.toeic.domain.*; // for static metamodels
import fpt.toeic.repository.SysLogsRepository;
import fpt.toeic.service.dto.SysLogsCriteria;
import fpt.toeic.service.dto.SysLogsDTO;
import fpt.toeic.service.mapper.SysLogsMapper;

/**
 * Service for executing complex queries for {@link SysLogs} entities in the database.
 * The main input is a {@link SysLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysLogsDTO} or a {@link Page} of {@link SysLogsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysLogsQueryService extends QueryService<SysLogs> {

    private final Logger log = LoggerFactory.getLogger(SysLogsQueryService.class);

    private final SysLogsRepository sysLogsRepository;

    private final SysLogsMapper sysLogsMapper;

    public SysLogsQueryService(SysLogsRepository sysLogsRepository, SysLogsMapper sysLogsMapper) {
        this.sysLogsRepository = sysLogsRepository;
        this.sysLogsMapper = sysLogsMapper;
    }

    /**
     * Return a {@link List} of {@link SysLogsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysLogsDTO> findByCriteria(SysLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysLogs> specification = createSpecification(criteria);
        return sysLogsMapper.toDto(sysLogsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysLogsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogsDTO> findByCriteria(SysLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysLogs> specification = createSpecification(criteria);
        return sysLogsRepository.findAll(specification, page)
            .map(sysLogsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysLogs> specification = createSpecification(criteria);
        return sysLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link SysLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysLogs> createSpecification(SysLogsCriteria criteria) {
        Specification<SysLogs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysLogs_.id));
            }
            if (criteria.getUserImpact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserImpact(), SysLogs_.userImpact));
            }
            if (criteria.getCodeAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeAction(), SysLogs_.codeAction));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), SysLogs_.content));
            }
            if (criteria.getImpactTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImpactTime(), SysLogs_.impactTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), SysLogs_.endTime));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), SysLogs_.ip));
            }
            if (criteria.getNameClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameClient(), SysLogs_.nameClient));
            }
        }
        return specification;
    }
}
