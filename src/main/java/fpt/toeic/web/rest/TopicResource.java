package fpt.toeic.web.rest;

import fpt.toeic.domain.Topic;
import fpt.toeic.domain.Users;
import fpt.toeic.domain.Users_;
import fpt.toeic.repository.TopicRepository;
import fpt.toeic.service.TopicQueryService;
import fpt.toeic.service.TopicService;
import fpt.toeic.service.dto.TopicCriteria;
import fpt.toeic.service.dto.TopicDTO;
import fpt.toeic.service.dto.UsersCriteria;
import fpt.toeic.service.dto.UsersDTO;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.JsonUtils;
import fpt.toeic.utils.Utils;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import liquibase.pro.packaged.E;
import liquibase.pro.packaged.S;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * REST controller for managing {@link fpt.toeic.domain.Topic}.
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

  private final Logger log = LoggerFactory.getLogger(TopicResource.class);

  private static final String ENTITY_NAME = "topic";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final TopicService topicService;

  private final TopicRepository topicRepository;

  private final TopicQueryService topicQueryService;

  public TopicResource(TopicService topicService, TopicRepository topicRepository, TopicQueryService topicQueryService) {
    this.topicService = topicService;
    this.topicRepository = topicRepository;
    this.topicQueryService = topicQueryService;
  }

    /**
     * {@code POST  /topics} : Create a new topic.
     *
     * @param topicDTO the topicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicDTO, or with status {@code 400 (Bad Request)} if the topic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic")
    public ResponseEntity<Object> createRoles(@Valid @RequestBody TopicDTO topicDTO, BindingResult result) throws URISyntaxException {
        log.debug("REST request to save Topic : {}", topicDTO);
        if (topicDTO.getId() != null) { // Kiểm tra id , khi client gửi sang chưa có id thì phải check
            throw new BadRequestAlertException("A new roles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            //Kiểm tra validate
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(result.getFieldError());
            } else {// Nếu không có lỗi thì xuông elsse
                topicDTO.setCreationTime(DateUtil.getDateC()); // current date
                topicService.save(topicDTO);
                return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code PUT  /roles} : Updates an existing roles.
     *
     * @param topicDTO the rolesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolesDTO,
     * or with status {@code 400 (Bad Request)} if the rolesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic")
    public ResponseEntity<Object> updateRoles(@Valid @RequestBody TopicDTO topicDTO, BindingResult result) throws URISyntaxException {
        log.debug("REST request to update Roles : {}", topicDTO);
        if (topicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(result.getFieldError());
            } else {
                Optional<Topic> topicDTO1 = topicService.findOne(topicDTO.getId());
                if (!topicDTO1.isPresent()) {
                    throw new IllegalArgumentException("Topic không tồn tại");
                }
                topicDTO.setCreationTime(topicDTO1.get().getCreationTime());
                topicDTO.setUpdateTime(DateUtil.getDateC());
                topicService.save(topicDTO);
                return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code GET  /roles} : get all the roles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @GetMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRoles(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            int page1 = reqParamObj.getInt("page");
            int page_size = reqParamObj.getInt("page_size");
            Long idType;
            Long idPartTopic;
            String name;
            try {
                idType = reqParamObj.getLong("idType");
            } catch (Exception e) {
                idType = null;
            }
            try {
                idPartTopic = reqParamObj.getLong("idPartTopic");
            } catch (Exception e) {
                idPartTopic = null;
            }
            try {
                name = reqParamObj.getString("name");
                if ("".equals(name.trim())) {
                    name = null;
                }
            } catch (Exception e) {
                name = null;
            }
            if (page1 != 0) {
                page1 = page1 * page_size;
            }
            List<TopicDTO> page = topicService.search(name, idType, idPartTopic, page1, page_size);
            long total = topicService.total(name, idType, idPartTopic);
            JSONArray arrayResult = new JSONArray();
            for (TopicDTO item : page) {
                JSONObject lstRoles = Utils.convertEntityToJSONObject(item);
                arrayResult.put(lstRoles);
            }
            JSONObject result = new JSONObject();
            result.put("count", total);
            result.put("list", arrayResult);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong quá trình tìm kiếm " + e.getMessage()));
        }
    }

    /**
     * {@code GET  /roles/count} : count all the roles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/topic/count")
    public ResponseEntity<Long> countRoles(TopicCriteria criteria) {
        log.debug("REST request to count Roles by criteria: {}", criteria);
        return ResponseEntity.ok().body(topicQueryService.countByCriteria(criteria));
    }

    @GetMapping("/lisTopic")
    public ResponseEntity<String> getRoles(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            TopicCriteria criteria = Utils.mappingCriteria(TopicCriteria.class, Utils.updateJsontoCamelCase(reqParamObj, true));
            log.debug("REST request to get Users by criteria: {}", criteria);
            Pageable pageable = Utils.getPage(reqParamObj, Users_.ID);
            Page<Topic> page = topicQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            JSONArray arrayResult = new JSONArray();
            for (Topic item : page) {
                TopicDTO topicDTO = new TopicDTO();
                topicDTO = item.toDto(item);
                try {
                    topicDTO.setIsLength(item.getDsCategories().size());
                } catch (Exception e) {
                    topicDTO.setIsLength(0);
                }
                JSONObject usersJson = Utils.convertEntityToJSONObject(topicDTO);
                arrayResult.put(usersJson);
            }
            JSONObject result = new JSONObject();
            result.put("list", arrayResult);
            return ResponseEntity.ok().headers(headers).body(Utils.getStatusOk("Thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong quá trình tìm kiếm " + e.getMessage()));
        }
    }

    /**
     * {@code DELETE  /roles/:id} : delete the "id" roles.
     *
     * @param id the id of the rolesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(value = "/topic/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRoles(@PathVariable Long id) {
        log.debug("REST request to delete Roles : {}", id);
        try {
            Optional<Topic> usersDTO = topicService.findOne(id);
            if (!usersDTO.isPresent()) {
                throw new IllegalArgumentException("Quyền không tồn tại");
            }
            topicService.delete(id);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong xoá: " + e.getMessage()));
        }

    }

    @GetMapping(value = "/topic/topicbypart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTopicByPart(@PathVariable Long id) {
        log.debug("REST request to get Topic by Part : {}", id);
        List<TopicDTO> topicDTO = topicService.getTopicByPart(id);
        JSONObject result = new JSONObject();
        result.put("list", topicDTO);
        return ResponseEntity.ok().body(result);

    }
}
