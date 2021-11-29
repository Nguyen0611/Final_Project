package fpt.toeic.web.rest;

import fpt.toeic.service.SysLogsService;
import fpt.toeic.web.rest.errors.BadRequestAlertException;
import fpt.toeic.service.dto.SysLogsDTO;
import fpt.toeic.service.dto.SysLogsCriteria;
import fpt.toeic.service.SysLogsQueryService;

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
 * REST controller for managing {@link fpt.toeic.domain.SysLogs}.
 */
@RestController
@RequestMapping("/api")
public class SysLogsResource {

    private final Logger log = LoggerFactory.getLogger(SysLogsResource.class);

    private static final String ENTITY_NAME = "sysLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysLogsService sysLogsService;

    private final SysLogsQueryService sysLogsQueryService;

    public SysLogsResource(SysLogsService sysLogsService, SysLogsQueryService sysLogsQueryService) {
        this.sysLogsService = sysLogsService;
        this.sysLogsQueryService = sysLogsQueryService;
    }

    /**
     * {@code POST  /sys-logs} : Create a new sysLogs.
     *
     * @param sysLogsDTO the sysLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysLogsDTO, or with status {@code 400 (Bad Request)} if the sysLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-logs")
    public ResponseEntity<SysLogsDTO> createSysLogs(@RequestBody SysLogsDTO sysLogsDTO) throws URISyntaxException {
        log.debug("REST request to save SysLogs : {}", sysLogsDTO);
        if (sysLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysLogsDTO result = sysLogsService.save(sysLogsDTO);
        return ResponseEntity.created(new URI("/api/sys-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-logs} : Updates an existing sysLogs.
     *
     * @param sysLogsDTO the sysLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysLogsDTO,
     * or with status {@code 400 (Bad Request)} if the sysLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-logs")
    public ResponseEntity<SysLogsDTO> updateSysLogs(@RequestBody SysLogsDTO sysLogsDTO) throws URISyntaxException {
        log.debug("REST request to update SysLogs : {}", sysLogsDTO);
        if (sysLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysLogsDTO result = sysLogsService.save(sysLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-logs} : get all the sysLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysLogs in body.
     */
    @GetMapping("/sys-logs")
    public ResponseEntity<List<SysLogsDTO>> getAllSysLogs(SysLogsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysLogs by criteria: {}", criteria);
        Page<SysLogsDTO> page = sysLogsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-logs/count} : count all the sysLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sys-logs/count")
    public ResponseEntity<Long> countSysLogs(SysLogsCriteria criteria) {
        log.debug("REST request to count SysLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysLogsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-logs/:id} : get the "id" sysLogs.
     *
     * @param id the id of the sysLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-logs/{id}")
    public ResponseEntity<SysLogsDTO> getSysLogs(@PathVariable Long id) {
        log.debug("REST request to get SysLogs : {}", id);
        Optional<SysLogsDTO> sysLogsDTO = sysLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysLogsDTO);
    }

    /**
     * {@code DELETE  /sys-logs/:id} : delete the "id" sysLogs.
     *
     * @param id the id of the sysLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-logs/{id}")
    public ResponseEntity<Void> deleteSysLogs(@PathVariable Long id) {
        log.debug("REST request to delete SysLogs : {}", id);
        sysLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
