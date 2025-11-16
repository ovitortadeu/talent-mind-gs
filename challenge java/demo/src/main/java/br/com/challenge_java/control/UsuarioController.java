package br.com.challenge_java.control;

import br.com.challenge_java.dto.UsuarioCreateDTO;
import br.com.challenge_java.dto.UsuarioDTO;
import br.com.challenge_java.dto.UsuarioUpdateDTO;
import br.com.challenge_java.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> criarNovoUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioDTO novoUsuario = usuarioService.registrarUsuario(usuarioCreateDTO);

        EntityModel<UsuarioDTO> resource = EntityModel.of(novoUsuario,
                linkTo(methodOn(UsuarioController.class).buscarUsuarioPorId(novoUsuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuariosPaginados(0, 10, "id", "ASC")).withRel("usuarios")
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> buscarUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarPorId(id);

        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioController.class).buscarUsuarioPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuariosPaginados(0, 10, "id", "ASC")).withRel("usuarios")
        );
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UsuarioDTO>>> listarUsuariosPaginados(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "username") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<UsuarioDTO> usuariosPage = usuarioService.listarTodosPaginado(pageable);

        List<EntityModel<UsuarioDTO>> usuarioResources = usuariosPage.getContent().stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).buscarUsuarioPorId(usuario.getId())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                usuariosPage.getSize(),
                usuariosPage.getNumber(),
                usuariosPage.getTotalElements(),
                usuariosPage.getTotalPages());

        PagedModel<EntityModel<UsuarioDTO>> pagedModel = PagedModel.of(usuarioResources, metadata,
                linkTo(methodOn(UsuarioController.class)
                        .listarUsuariosPaginados(page, size, sortBy, direction)).withSelfRel());

        if (usuariosPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(UsuarioController.class)
                    .listarUsuariosPaginados(usuariosPage.previousOrFirstPageable().getPageNumber(), size, sortBy, direction)).withRel("prev"));
        }
        if (usuariosPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(UsuarioController.class)
                    .listarUsuariosPaginados(usuariosPage.nextOrLastPageable().getPageNumber(), size, sortBy, direction)).withRel("next"));
        }
        if (!usuariosPage.isFirst()) {
             pagedModel.add(linkTo(methodOn(UsuarioController.class)
                    .listarUsuariosPaginados(0, size, sortBy, direction)).withRel("first"));
        }
        if (!usuariosPage.isLast()) {
            pagedModel.add(linkTo(methodOn(UsuarioController.class)
                    .listarUsuariosPaginados(usuariosPage.getTotalPages() -1, size, sortBy, direction)).withRel("last"));
        }


        return ResponseEntity.ok(pagedModel);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioUpdateDTO);
        EntityModel<UsuarioDTO> resource = EntityModel.of(usuarioAtualizado,
                linkTo(methodOn(UsuarioController.class).buscarUsuarioPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuariosPaginados(0, 10, "id", "ASC")).withRel("usuarios")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}