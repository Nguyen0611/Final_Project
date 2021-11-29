package fpt.toeic.web.rest;

import fpt.toeic.domain.Roles;
import fpt.toeic.domain.Roles_;
import fpt.toeic.service.RolesService;
import fpt.toeic.utils.DateUtil;
import fpt.toeic.utils.JsonUtils;
import fpt.toeic.utils.Utils;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import fpt.toeic.service.dto.RolesDTO;
import fpt.toeic.service.dto.RolesCriteria;
import fpt.toeic.service.RolesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
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
 * REST controller for managing {@link fpt.toeic.domain.Roles}.
 */
@RestController
@RequestMapping("/api")
public class RolesResource {

    private final Logger log = LoggerFactory.getLogger(RolesResource.class);

    private static final String ENTITY_NAME = "roles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolesService rolesService;

    private final RolesQueryService rolesQueryService;

    public RolesResource(RolesService rolesService, RolesQueryService rolesQueryService) {
        this.rolesService = rolesService;
        this.rolesQueryService = rolesQueryService;
    }

    /**
     * {@code POST  /roles} : Create a new roles.
     *
     * @param rolesDTO the rolesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolesDTO, or with status {@code 400 (Bad Request)} if the roles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roles")
    public ResponseEntity<Object> createRoles(@Valid @RequestBody RolesDTO rolesDTO, BindingResult result) throws URISyntaxException {
        log.debug("REST request to save Roles : {}", rolesDTO);
        if (rolesDTO.getId() != null) {
            throw new BadRequestAlertException("A new roles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body( result.getFieldError());
            } else {
                Roles roles = rolesService.findByCode(rolesDTO.getCode());
                if (roles != null){
                    throw new IllegalArgumentException("Mã quyền đã tồn tại");
                }
                rolesService.save(rolesDTO);
                return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body( Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code PUT  /roles} : Updates an existing roles.
     *
     * @param rolesDTO the rolesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolesDTO,
     * or with status {@code 400 (Bad Request)} if the rolesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roles")
    public ResponseEntity<Object> updateRoles(@Valid @RequestBody RolesDTO rolesDTO, BindingResult result) throws URISyntaxException {
        log.debug("REST request to update Roles : {}", rolesDTO);
        if (rolesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body( result.getFieldError());
            } else {
                Optional<RolesDTO> users = rolesService.findOne(rolesDTO.getId());
                RolesDTO rolesDTO1 = users.get();
                if (!rolesDTO1.getCode().equals(rolesDTO.getCode())){
                    Roles roles = rolesService.findByCode(rolesDTO.getCode());
                    if (roles != null){
                        throw new IllegalArgumentException("Mã quyền đã tồn tại");
                    }
                }
                rolesDTO.setUpdateTime(DateUtil.getDateC());
                rolesService.save(rolesDTO);
                return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body( Utils.getStatusBadRequest(e.getMessage()));
        }
    }

    /**
     * {@code GET  /roles} : get all the roles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRoles(@RequestParam Map<String, String> paramSearch) {
        try {
            JSONObject reqParamObj = JsonUtils.transferJsonKey(new JSONObject(paramSearch), true);
            RolesCriteria criteria = Utils.mappingCriteria(RolesCriteria.class, Utils.updateJsontoCamelCase(reqParamObj, true));
            log.debug("REST request to get Users by criteria: {}", criteria);
            Pageable pageable = Utils.getPage(reqParamObj, Roles_.TYPE);
            Page<RolesDTO> page = rolesQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            JSONArray arrayResult = new JSONArray();
            for (RolesDTO item : page) {
                JSONObject lstRoles = Utils.convertEntityToJSONObject(item);
                arrayResult.put(lstRoles);
            }
            JSONObject result = new JSONObject();
            result.put("count", rolesQueryService.countByCriteria(criteria));
            result.put("list", arrayResult);
            return ResponseEntity.ok().headers(headers).body(Utils.getStatusOk("Thành công", result));
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
    @GetMapping("/roles/count")
    public ResponseEntity<Long> countRoles(RolesCriteria criteria) {
        log.debug("REST request to count Roles by criteria: {}", criteria);
        return ResponseEntity.ok().body(rolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /roles/:id} : get the "id" roles.
     *
     * @param id the id of the rolesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<RolesDTO> getRoles(@PathVariable Long id) {
        log.debug("REST request to get Roles : {}", id);
        Optional<RolesDTO> rolesDTO = rolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolesDTO);
    }

    /**
     * {@code DELETE  /roles/:id} : delete the "id" roles.
     *
     * @param id the id of the rolesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRoles(@PathVariable Long id) {
        log.debug("REST request to delete Roles : {}", id);
        try {
            Optional<RolesDTO> usersDTO = rolesService.findOne(id);
            if (!usersDTO.isPresent()) {
                throw new IllegalArgumentException("Quyền không tồn tại");
            }
            rolesService.delete(id);
            return ResponseEntity.ok().body(Utils.getStatusOk("Thành công", null));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                Utils.getStatusBadRequest("Có lỗi xảy ra trong xoá: " + e.getMessage()));
        }

    }
}
