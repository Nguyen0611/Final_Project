package fpt.toeic.web.rest;

import fpt.toeic.service.RoleObjectService;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import fpt.toeic.service.dto.RoleObjectDTO;
import fpt.toeic.service.dto.RoleObjectCriteria;
import fpt.toeic.service.RoleObjectQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.toeic.domain.RoleObject}.
 */
@RestController
@RequestMapping("/api")
public class RoleObjectResource {

    private final Logger log = LoggerFactory.getLogger(RoleObjectResource.class);

    private static final String ENTITY_NAME = "roleObject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleObjectService roleObjectService;

    private final RoleObjectQueryService roleObjectQueryService;

    public RoleObjectResource(RoleObjectService roleObjectService, RoleObjectQueryService roleObjectQueryService) {
        this.roleObjectService = roleObjectService;
        this.roleObjectQueryService = roleObjectQueryService;
    }

    /**
     * {@code POST  /role-objects} : Create a new roleObject.
     *
     * @param roleObjectDTO the roleObjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleObjectDTO, or with status {@code 400 (Bad Request)} if the roleObject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-objects")
    public ResponseEntity<RoleObjectDTO> createRoleObject(@RequestBody RoleObjectDTO roleObjectDTO) throws URISyntaxException {
        log.debug("REST request to save RoleObject : {}", roleObjectDTO);
        if (roleObjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleObjectDTO result = roleObjectService.save(roleObjectDTO);
        return ResponseEntity.created(new URI("/api/role-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-objects} : Updates an existing roleObject.
     *
     * @param roleObjectDTO the roleObjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleObjectDTO,
     * or with status {@code 400 (Bad Request)} if the roleObjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleObjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-objects")
    public ResponseEntity<RoleObjectDTO> updateRoleObject(@RequestBody RoleObjectDTO roleObjectDTO) throws URISyntaxException {
        log.debug("REST request to update RoleObject : {}", roleObjectDTO);
        if (roleObjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleObjectDTO result = roleObjectService.save(roleObjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /role-objects} : get all the roleObjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleObjects in body.
     */
    @GetMapping("/role-objects")
    public ResponseEntity<List<RoleObjectDTO>> getAllRoleObjects(RoleObjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RoleObjects by criteria: {}", criteria);
        Page<RoleObjectDTO> page = roleObjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-objects/count} : count all the roleObjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/role-objects/count")
    public ResponseEntity<Long> countRoleObjects(RoleObjectCriteria criteria) {
        log.debug("REST request to count RoleObjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(roleObjectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /role-objects/:id} : get the "id" roleObject.
     *
     * @param id the id of the roleObjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleObjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-objects/{id}")
    public ResponseEntity<RoleObjectDTO> getRoleObject(@PathVariable Long id) {
        log.debug("REST request to get RoleObject : {}", id);
        Optional<RoleObjectDTO> roleObjectDTO = roleObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleObjectDTO);
    }

    /**
     * {@code DELETE  /role-objects/:id} : delete the "id" roleObject.
     *
     * @param id the id of the roleObjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-objects/{id}")
    public ResponseEntity<Void> deleteRoleObject(@PathVariable Long id) {
        log.debug("REST request to delete RoleObject : {}", id);
        roleObjectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
