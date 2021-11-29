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

import fpt.toeic.domain.Objects;
import fpt.toeic.domain.*; // for static metamodels
import fpt.toeic.repository.ObjectsRepository;
import fpt.toeic.service.dto.ObjectsCriteria;
import fpt.toeic.service.dto.ObjectsDTO;
import fpt.toeic.service.mapper.ObjectsMapper;

/**
 * Service for executing complex queries for {@link Objects} entities in the database.
 * The main input is a {@link ObjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObjectsDTO} or a {@link Page} of {@link ObjectsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObjectsQueryService extends QueryService<Objects> {

    private final Logger log = LoggerFactory.getLogger(ObjectsQueryService.class);

    private final ObjectsRepository objectsRepository;

    private final ObjectsMapper objectsMapper;

    public ObjectsQueryService(ObjectsRepository objectsRepository, ObjectsMapper objectsMapper) {
        this.objectsRepository = objectsRepository;
        this.objectsMapper = objectsMapper;
    }

    /**
     * Return a {@link List} of {@link ObjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObjectsDTO> findByCriteria(ObjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Objects> specification = createSpecification(criteria);
        return objectsMapper.toDto(objectsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObjectsDTO> findByCriteria(ObjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Objects> specification = createSpecification(criteria);
        return objectsRepository.findAll(specification, page)
            .map(objectsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Objects> specification = createSpecification(criteria);
        return objectsRepository.count(specification);
    }

    /**
     * Function to convert {@link ObjectsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Objects> createSpecification(ObjectsCriteria criteria) {
        Specification<Objects> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Objects_.id));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentId(), Objects_.parentId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Objects_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Objects_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Objects_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Objects_.status));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), Objects_.icon));
            }
            if (criteria.getPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPath(), Objects_.path));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), Objects_.updateTime));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), Objects_.type));
            }
            if (criteria.getDsRoleObjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getDsRoleObjectId(),
                    root -> root.join(Objects_.dsRoleObjects, JoinType.LEFT).get(RoleObject_.id)));
            }
        }
        return specification;
    }
}
