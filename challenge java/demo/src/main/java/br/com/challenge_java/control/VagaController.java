package br.com.challenge_java.control;

import br.com.challenge_java.dto.VagaDTO;
import br.com.challenge_java.service.VagaService;
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
@RequestMapping("/api/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @PostMapping
    public ResponseEntity<EntityModel<VagaDTO>> criarVaga(@RequestBody @Valid VagaDTO vagaDTO) {
        VagaDTO novaVaga = vagaService.criar(vagaDTO);
        EntityModel<VagaDTO> resource = EntityModel.of(novaVaga,
                linkTo(methodOn(VagaController.class).buscarVagaPorId(novaVaga.getId())).withSelfRel(),
                linkTo(methodOn(VagaController.class).listarVagasPaginadas(Pageable.unpaged())).withRel("vagas"));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaVaga.getId())
                .toUri();
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VagaDTO>> buscarVagaPorId(@PathVariable Long id) {
        VagaDTO vagaDTO = vagaService.buscarPorId(id);
        EntityModel<VagaDTO> resource = EntityModel.of(vagaDTO,
                linkTo(methodOn(VagaController.class).buscarVagaPorId(id)).withSelfRel(),
                linkTo(methodOn(VagaController.class).listarVagasPaginadas(Pageable.unpaged())).withRel("vagas"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<VagaDTO>>> listarVagasPaginadas(
            @PageableDefault(size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<VagaDTO> vagasPage = vagaService.listarTodas(pageable);
        
        PagedModel<EntityModel<VagaDTO>> pagedModel = PagedModel.of(
            vagasPage.getContent().stream()
                .map(vaga -> EntityModel.of(vaga,
                    linkTo(methodOn(VagaController.class).buscarVagaPorId(vaga.getId())).withSelfRel()))
                .toList(),
            new PagedModel.PageMetadata(
                vagasPage.getSize(),
                vagasPage.getNumber(),
                vagasPage.getTotalElements(),
                vagasPage.getTotalPages()),
            linkTo(methodOn(VagaController.class).listarVagasPaginadas(pageable)).withSelfRel()
        );

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<VagaDTO>> atualizarVaga(@PathVariable Long id, @RequestBody @Valid VagaDTO vagaDTO) {
        VagaDTO vagaAtualizada = vagaService.atualizar(id, vagaDTO);
        EntityModel<VagaDTO> resource = EntityModel.of(vagaAtualizada,
                linkTo(methodOn(VagaController.class).buscarVagaPorId(id)).withSelfRel(),
                linkTo(methodOn(VagaController.class).listarVagasPaginadas(Pageable.unpaged())).withRel("vagas"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVaga(@PathVariable Long id) {
        vagaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}