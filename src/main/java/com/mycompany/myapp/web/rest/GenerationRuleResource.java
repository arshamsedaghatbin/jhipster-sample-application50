package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.GenerationRuleRepository;
import com.mycompany.myapp.service.GenerationRuleService;
import com.mycompany.myapp.service.dto.GenerationRuleDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.GenerationRule}.
 */
@RestController
@RequestMapping("/api")
public class GenerationRuleResource {

    private final Logger log = LoggerFactory.getLogger(GenerationRuleResource.class);

    private static final String ENTITY_NAME = "generationRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenerationRuleService generationRuleService;

    private final GenerationRuleRepository generationRuleRepository;

    public GenerationRuleResource(GenerationRuleService generationRuleService, GenerationRuleRepository generationRuleRepository) {
        this.generationRuleService = generationRuleService;
        this.generationRuleRepository = generationRuleRepository;
    }

    /**
     * {@code POST  /generation-rules} : Create a new generationRule.
     *
     * @param generationRuleDTO the generationRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generationRuleDTO, or with status {@code 400 (Bad Request)} if the generationRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generation-rules")
    public ResponseEntity<GenerationRuleDTO> createGenerationRule(@Valid @RequestBody GenerationRuleDTO generationRuleDTO)
        throws URISyntaxException {
        log.debug("REST request to save GenerationRule : {}", generationRuleDTO);
        if (generationRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new generationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenerationRuleDTO result = generationRuleService.save(generationRuleDTO);
        return ResponseEntity
            .created(new URI("/api/generation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /generation-rules/:id} : Updates an existing generationRule.
     *
     * @param id the id of the generationRuleDTO to save.
     * @param generationRuleDTO the generationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the generationRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/generation-rules/{id}")
    public ResponseEntity<GenerationRuleDTO> updateGenerationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GenerationRuleDTO generationRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GenerationRule : {}, {}", id, generationRuleDTO);
        if (generationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GenerationRuleDTO result = generationRuleService.save(generationRuleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, generationRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /generation-rules/:id} : Partial updates given fields of an existing generationRule, field will ignore if it is null
     *
     * @param id the id of the generationRuleDTO to save.
     * @param generationRuleDTO the generationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the generationRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the generationRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the generationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/generation-rules/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GenerationRuleDTO> partialUpdateGenerationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GenerationRuleDTO generationRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GenerationRule partially : {}, {}", id, generationRuleDTO);
        if (generationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GenerationRuleDTO> result = generationRuleService.partialUpdate(generationRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, generationRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /generation-rules} : get all the generationRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generationRules in body.
     */
    @GetMapping("/generation-rules")
    public ResponseEntity<List<GenerationRuleDTO>> getAllGenerationRules(Pageable pageable) {
        log.debug("REST request to get a page of GenerationRules");
        Page<GenerationRuleDTO> page = generationRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /generation-rules/:id} : get the "id" generationRule.
     *
     * @param id the id of the generationRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generationRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generation-rules/{id}")
    public ResponseEntity<GenerationRuleDTO> getGenerationRule(@PathVariable Long id) {
        log.debug("REST request to get GenerationRule : {}", id);
        Optional<GenerationRuleDTO> generationRuleDTO = generationRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generationRuleDTO);
    }

    /**
     * {@code DELETE  /generation-rules/:id} : delete the "id" generationRule.
     *
     * @param id the id of the generationRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/generation-rules/{id}")
    public ResponseEntity<Void> deleteGenerationRule(@PathVariable Long id) {
        log.debug("REST request to delete GenerationRule : {}", id);
        generationRuleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
