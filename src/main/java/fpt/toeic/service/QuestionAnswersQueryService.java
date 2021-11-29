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

import fpt.toeic.domain.QuestionAnswers;
import fpt.toeic.domain.*; // for static metamodels
import fpt.toeic.repository.QuestionAnswersRepository;
import fpt.toeic.service.dto.QuestionAnswersCriteria;
import fpt.toeic.service.dto.QuestionAnswersDTO;
import fpt.toeic.service.mapper.QuestionAnswersMapper;

/**
 * Service for executing complex queries for {@link QuestionAnswers} entities in the database.
 * The main input is a {@link QuestionAnswersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionAnswersDTO} or a {@link Page} of {@link QuestionAnswersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionAnswersQueryService extends QueryService<QuestionAnswers> {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswersQueryService.class);

    private final QuestionAnswersRepository questionAnswersRepository;

    private final QuestionAnswersMapper questionAnswersMapper;

    public QuestionAnswersQueryService(QuestionAnswersRepository questionAnswersRepository, QuestionAnswersMapper questionAnswersMapper) {
        this.questionAnswersRepository = questionAnswersRepository;
        this.questionAnswersMapper = questionAnswersMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionAnswersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionAnswersDTO> findByCriteria(QuestionAnswersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionAnswers> specification = createSpecification(criteria);
        return questionAnswersMapper.toDto(questionAnswersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionAnswersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionAnswersDTO> findByCriteria(QuestionAnswersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionAnswers> specification = createSpecification(criteria);
        return questionAnswersRepository.findAll(specification, page)
            .map(questionAnswersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionAnswersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionAnswers> specification = createSpecification(criteria);
        return questionAnswersRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionAnswersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionAnswers> createSpecification(QuestionAnswersCriteria criteria) {
        Specification<QuestionAnswers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionAnswers_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionAnswers_.name));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), QuestionAnswers_.answer));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), QuestionAnswers_.status));
            }
            if (criteria.getStt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStt(), QuestionAnswers_.stt));
            }
            if (criteria.getAnswerToChoose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswerToChoose(), QuestionAnswers_.answerToChoose));
            }
            if (criteria.getTransscript() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransscript(), QuestionAnswers_.transscript));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(QuestionAnswers_.category, JoinType.LEFT).get(Category_.id)));
            }
        }
        return specification;
    }
}
