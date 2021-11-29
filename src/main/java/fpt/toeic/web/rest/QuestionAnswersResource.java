package fpt.toeic.web.rest;

import fpt.toeic.domain.Category;
import fpt.toeic.repository.CategoryRepository;
import fpt.toeic.service.CategoryService;
import fpt.toeic.service.QuestionAnswersQueryService;
import fpt.toeic.service.QuestionAnswersService;
import fpt.toeic.service.dto.QuestionAnswersCriteria;
import fpt.toeic.service.dto.QuestionAnswersDTO;
import fpt.toeic.utils.JsonUtils;
import fpt.toeic.utils.Utils;
import fpt.toeic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.toeic.domain.QuestionAnswers}.
 */
@RestController
@RequestMapping("/api")
public class QuestionAnswersResource {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswersResource.class);

    private static final String ENTITY_NAME = "questionAnswers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionAnswersService questionAnswersService;

    private final QuestionAnswersQueryService questionAnswersQueryService;

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    public QuestionAnswersResource(QuestionAnswersService questionAnswersService,
                                   CategoryRepository categoryRepository,
                                   QuestionAnswersQueryService questionAnswersQueryService,
                                   CategoryService categoryService) {
        this.questionAnswersService = questionAnswersService;
        this.questionAnswersQueryService = questionAnswersQueryService;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    /**
     * {@code POST  /question-answers} : Create a new questionAnswers.
     *
     * @param questionAnswersDTO the questionAnswersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionAnswersDTO, or with status {@code 400 (Bad Request)} if the questionAnswers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-answers")
    public ResponseEntity<QuestionAnswersDTO> createQuestionAnswers(@Valid @RequestBody QuestionAnswersDTO questionAnswersDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionAnswers : {}", questionAnswersDTO);
        if (questionAnswersDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionAnswers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionAnswersDTO result = questionAnswersService.save(questionAnswersDTO);
        return ResponseEntity.created(new URI("/api/question-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-answers} : Updates an existing questionAnswers.
     *
     * @param questionAnswersDTO the questionAnswersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionAnswersDTO,
     * or with status {@code 400 (Bad Request)} if the questionAnswersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionAnswersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-answers")
    public ResponseEntity<QuestionAnswersDTO> updateQuestionAnswers(@Valid @RequestBody QuestionAnswersDTO questionAnswersDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionAnswers : {}", questionAnswersDTO);
        if (questionAnswersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionAnswersDTO result = questionAnswersService.save(questionAnswersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionAnswersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-answers} : get all the questionAnswers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionAnswers in body.
     */
    @GetMapping("/question-answers")
    public ResponseEntity<List<QuestionAnswersDTO>> getAllQuestionAnswers(QuestionAnswersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionAnswers by criteria: {}", criteria);
        Page<QuestionAnswersDTO> page = questionAnswersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-answers/count} : count all the questionAnswers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-answers/count")
    public ResponseEntity<Long> countQuestionAnswers(QuestionAnswersCriteria criteria) {
        log.debug("REST request to count QuestionAnswers by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionAnswersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-answers/:id} : get the "id" questionAnswers.
     *
     * @param id the id of the questionAnswersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionAnswersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-answers/{id}")
    public ResponseEntity<QuestionAnswersDTO> getQuestionAnswers(@PathVariable Long id) {
        log.debug("REST request to get QuestionAnswers : {}", id);
        Optional<QuestionAnswersDTO> questionAnswersDTO = questionAnswersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionAnswersDTO);
    }

    /**
     * {@code DELETE  /question-answers/:id} : delete the "id" questionAnswers.
     *
     * @param id the id of the questionAnswersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-answers/{id}")
    public ResponseEntity<Void> deleteQuestionAnswers(@PathVariable Long id) {
        log.debug("REST request to delete QuestionAnswers : {}", id);
        questionAnswersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(value = "/question-answers-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getQuestionAnswersByCategory(@RequestParam Map<String, String> paramSearch) {
        try{
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            Long id;
            try {
                id = reqParamObj.getLong("id");
            } catch (Exception e) {
                id = null;
            }
            return ResponseEntity.ok().body(questionAnswersService.findAllQuestionsByTopic(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi tìm câu hỏi" + e.getMessage()));
        }
    }

    @GetMapping(value = "/list-question-answers-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListQuestionAnswersByCategory(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            Long id;
            try {
                id = reqParamObj.getLong("categoryId");
            } catch (Exception e) {
                id = null;
            }
            return ResponseEntity.ok().body(questionAnswersService.findAllQuestionsClient(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi tìm câu hỏi" + e.getMessage()));
        }
    }

    @GetMapping(value = "/is-check-question-answers-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> isCheckQuestionAnswersByCategory(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            Long id;
            try {
                id = reqParamObj.getLong("categoryId");
            } catch (Exception e) {
                id = null;
            }
            String value;
            try {
                value = reqParamObj.getString("value");
            } catch (Exception e) {
                value = null;
            }
            Category category = categoryRepository.findCategoryById(id);
            if (category == null) {
                throw new IllegalArgumentException("Có lỗi kiểm tra câu hỏi với chủ đề");
            }
            return ResponseEntity.ok().body(questionAnswersService.findByStatusAndCategory(1L, category, value));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi tìm câu hỏi" + e.getMessage()));
        }
    }

    @GetMapping(value = "/question-answers-part6", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Map<String, List<QuestionAnswersDTO>>>> getQuestionAnswersByCategoryPart6(@RequestParam Map<String, String> paramSearch) {
        try{
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            Long id;
            try {
                id = reqParamObj.getLong("id");
            } catch (Exception e) {
                id = null;
            }

            return ResponseEntity.ok().body(questionAnswersService.findAllQuestionsByTopicPart6(id));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
