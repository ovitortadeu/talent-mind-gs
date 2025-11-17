package br.com.challenge_java.control;

import br.com.challenge_java.dto.CursoRequalificacaoDTO;
import br.com.challenge_java.service.CursoRequalificacaoService;
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
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoRequalificacaoController {

    private final CursoRequalificacaoService cursoService;

    @PostMapping
    public ResponseEntity<EntityModel<CursoRequalificacaoDTO>> criarCurso(@RequestBody @Valid CursoRequalificacaoDTO cursoDTO) {
        CursoRequalificacaoDTO novoCurso = cursoService.criar(cursoDTO);
        EntityModel<CursoRequalificacaoDTO> resource = EntityModel.of(novoCurso,
                linkTo(methodOn(CursoRequalificacaoController.class).buscarCursoPorId(novoCurso.getId())).withSelfRel(),
                linkTo(methodOn(CursoRequalificacaoController.class).listarCursosPaginados(Pageable.unpaged())).withRel("cursos"));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCurso.getId())
                .toUri();
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CursoRequalificacaoDTO>> buscarCursoPorId(@PathVariable Long id) {
        CursoRequalificacaoDTO cursoDTO = cursoService.buscarPorId(id);
        EntityModel<CursoRequalificacaoDTO> resource = EntityModel.of(cursoDTO,
                linkTo(methodOn(CursoRequalificacaoController.class).buscarCursoPorId(id)).withSelfRel(),
                linkTo(methodOn(CursoRequalificacaoController.class).listarCursosPaginados(Pageable.unpaged())).withRel("cursos"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CursoRequalificacaoDTO>>> listarCursosPaginados(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<CursoRequalificacaoDTO> cursosPage = cursoService.listarTodos(pageable);
        
        PagedModel<EntityModel<CursoRequalificacaoDTO>> pagedModel = PagedModel.of(
            cursosPage.getContent().stream()
                .map(curso -> EntityModel.of(curso,
                    linkTo(methodOn(CursoRequalificacaoController.class).buscarCursoPorId(curso.getId())).withSelfRel()))
                .toList(),
            new PagedModel.PageMetadata(
                cursosPage.getSize(),
                cursosPage.getNumber(),
                cursosPage.getTotalElements(),
                cursosPage.getTotalPages()),
            linkTo(methodOn(CursoRequalificacaoController.class).listarCursosPaginados(pageable)).withSelfRel()
        );

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CursoRequalificacaoDTO>> atualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoRequalificacaoDTO cursoDTO) {
        CursoRequalificacaoDTO cursoAtualizado = cursoService.atualizar(id, cursoDTO);
        EntityModel<CursoRequalificacaoDTO> resource = EntityModel.of(cursoAtualizado,
                linkTo(methodOn(CursoRequalificacaoController.class).buscarCursoPorId(id)).withSelfRel(),
                linkTo(methodOn(CursoRequalificacaoController.class).listarCursosPaginados(Pageable.unpaged())).withRel("cursos"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}