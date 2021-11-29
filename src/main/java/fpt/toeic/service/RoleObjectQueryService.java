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

import fpt.toeic.domain.RoleObject;
import fpt.toeic.domain.*; // for static metamodels
import fpt.toeic.repository.RoleObjectRepository;
import fpt.toeic.service.dto.RoleObjectCriteria;
import fpt.toeic.service.dto.RoleObjectDTO;
import fpt.toeic.service.mapper.RoleObjectMapper;

/**
 * Service for executing complex queries for {@link RoleObject} entities in the database.
 * The main input is a {@link RoleObjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleObjectDTO} or a {@link Page} of {@link RoleObjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleObjectQueryService extends QueryService<RoleObject> {

    private final Logger log = LoggerFactory.getLogger(RoleObjectQueryService.class);

    private final RoleObjectRepository roleObjectRepository;

    private final RoleObjectMapper roleObjectMapper;

    public RoleObjectQueryService(RoleObjectRepository roleObjectRepository, RoleObjectMapper roleObjectMapper) {
        this.roleObjectRepository = roleObjectRepository;
        this.roleObjectMapper = roleObjectMapper;
    }

    /**
     * Return a {@link List} of {@link RoleObjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleObjectDTO> findByCriteria(RoleObjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoleObject> specification = createSpecification(criteria);
        return roleObjectMapper.toDto(roleObjectRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoleObjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleObjectDTO> findByCriteria(RoleObjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoleObject> specification = createSpecification(criteria);
        return roleObjectRepository.findAll(specification, page)
            .map(roleObjectMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoleObjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoleObject> specification = createSpecification(criteria);
        return roleObjectRepository.count(specification);
    }

    /**
     * Function to convert {@link RoleObjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoleObject> createSpecification(RoleObjectCriteria criteria) {
        Specification<RoleObject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoleObject_.id));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), RoleObject_.updateTime));
            }
            if (criteria.getRolesId() != null) {
                specification = specification.and(buildSpecification(criteria.getRolesId(),
                    root -> root.join(RoleObject_.roles, JoinType.LEFT).get(Roles_.id)));
            }
            if (criteria.getObjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getObjectsId(),
                    root -> root.join(RoleObject_.objects, JoinType.LEFT).get(Objects_.id)));
            }
        }
        return specification;
    }
}
