package br.com.challenge_java.control;

import br.com.challenge_java.dto.CompetenciaDTO;
import br.com.challenge_java.service.CompetenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/competencias")
@RequiredArgsConstructor
public class CompetenciaController {

    private final CompetenciaService competenciaService;

    @PostMapping
    public ResponseEntity<EntityModel<CompetenciaDTO>> criarCompetencia(@RequestBody @Valid CompetenciaDTO competenciaDTO) {
        CompetenciaDTO novaCompetencia = competenciaService.criar(competenciaDTO);
        EntityModel<CompetenciaDTO> resource = EntityModel.of(novaCompetencia,
                linkTo(methodOn(CompetenciaController.class).buscarCompetenciaPorId(novaCompetencia.getId())).withSelfRel(),
                linkTo(methodOn(CompetenciaController.class).listarCompetenciasPaginadas(Pageable.unpaged())).withRel("competencias"));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaCompetencia.getId())
                .toUri();
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CompetenciaDTO>> buscarCompetenciaPorId(@PathVariable Long id) {
        CompetenciaDTO competenciaDTO = competenciaService.buscarPorId(id);
        EntityModel<CompetenciaDTO> resource = EntityModel.of(competenciaDTO,
                linkTo(methodOn(CompetenciaController.class).buscarCompetenciaPorId(id)).withSelfRel(),
                linkTo(methodOn(CompetenciaController.class).listarCompetenciasPaginadas(Pageable.unpaged())).withRel("competencias"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CompetenciaDTO>>> listarCompetenciasPaginadas(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<CompetenciaDTO> competenciasPage = competenciaService.listarTodas(pageable);
        
        PagedModel<EntityModel<CompetenciaDTO>> pagedModel = PagedModel.of(
            competenciasPage.getContent().stream()
                .map(comp -> EntityModel.of(comp,
                    linkTo(methodOn(CompetenciaController.class).buscarCompetenciaPorId(comp.getId())).withSelfRel()))
                .toList(),
            new PagedModel.PageMetadata(
                competenciasPage.getSize(),
                competenciasPage.getNumber(),
                competenciasPage.getTotalElements(),
                competenciasPage.getTotalPages()),
            linkTo(methodOn(CompetenciaController.class).listarCompetenciasPaginadas(pageable)).withSelfRel()
        );

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CompetenciaDTO>> atualizarCompetencia(@PathVariable Long id, @RequestBody @Valid CompetenciaDTO competenciaDTO) {
        CompetenciaDTO competenciaAtualizada = competenciaService.atualizar(id, competenciaDTO);
        EntityModel<CompetenciaDTO> resource = EntityModel.of(competenciaAtualizada,
                linkTo(methodOn(CompetenciaController.class).buscarCompetenciaPorId(id)).withSelfRel(),
                linkTo(methodOn(CompetenciaController.class).listarCompetenciasPaginadas(Pageable.unpaged())).withRel("competencias"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCompetencia(@PathVariable Long id) {
        competenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}