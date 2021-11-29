package fpt.toeic.web.rest;

import fpt.toeic.domain.Objects;
import fpt.toeic.service.ObjectsService;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import fpt.toeic.service.dto.ObjectsDTO;
import fpt.toeic.service.dto.ObjectsCriteria;
import fpt.toeic.service.ObjectsQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.toeic.domain.Objects}.
 */
@RestController
@RequestMapping("/api")
public class ObjectsResource {

    private final Logger log = LoggerFactory.getLogger(ObjectsResource.class);

    private static final String ENTITY_NAME = "objects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObjectsService objectsService;

    private final ObjectsQueryService objectsQueryService;

    public ObjectsResource(ObjectsService objectsService, ObjectsQueryService objectsQueryService) {
        this.objectsService = objectsService;
        this.objectsQueryService = objectsQueryService;
    }

    /**
     * {@code POST  /objects} : Create a new objects.
     *
     * @param objectsDTO the objectsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objectsDTO, or with status {@code 400 (Bad Request)} if the objects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/objects")
    public ResponseEntity<ObjectsDTO> createObjects(@Valid @RequestBody ObjectsDTO objectsDTO) throws URISyntaxException {
        log.debug("REST request to save Objects : {}", objectsDTO);
        if (objectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new objects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObjectsDTO result = objectsService.save(objectsDTO);
        return ResponseEntity.created(new URI("/api/objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /objects} : Updates an existing objects.
     *
     * @param objectsDTO the objectsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectsDTO,
     * or with status {@code 400 (Bad Request)} if the objectsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objectsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/objects")
    public ResponseEntity<ObjectsDTO> updateObjects(@Valid @RequestBody ObjectsDTO objectsDTO) throws URISyntaxException {
        log.debug("REST request to update Objects : {}", objectsDTO);
        if (objectsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObjectsDTO result = objectsService.save(objectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, objectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /objects} : get all the objects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objects in body.
     */
    @GetMapping("/objects")
    public ResponseEntity<List<ObjectsDTO>> getAllObjects(ObjectsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Objects by criteria: {}", criteria);
        Page<ObjectsDTO> page = objectsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/objects-topic/{id}")
    public ResponseEntity<List<ObjectsDTO>> getAllObjectsTopicId(@PathVariable Long id) {
        List<ObjectsDTO> page = objectsService.findAllByParentIdAndStatus(id);
        return ResponseEntity.ok().body(page);
    }
    /**
     * {@code GET  /objects/count} : count all the objects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/objects/count")
    public ResponseEntity<Long> countObjects(ObjectsCriteria criteria) {
        log.debug("REST request to count Objects by criteria: {}", criteria);
        return ResponseEntity.ok().body(objectsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /objects/:id} : get the "id" objects.
     *
     * @param id the id of the objectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objectsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/objects/{id}")
    public ResponseEntity<ObjectsDTO> getObjects(@PathVariable Long id) {
        log.debug("REST request to get Objects : {}", id);
        Optional<ObjectsDTO> objectsDTO = objectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objectsDTO);
    }

    /**
     * {@code DELETE  /objects/:id} : delete the "id" objects.
     *
     * @param id the id of the objectsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/objects/{id}")
    public ResponseEntity<Void> deleteObjects(@PathVariable Long id) {
        log.debug("REST request to delete Objects : {}", id);
        objectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
