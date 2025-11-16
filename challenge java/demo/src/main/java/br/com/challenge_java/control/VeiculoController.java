package br.com.challenge_java.control;

import br.com.challenge_java.dto.VeiculoCreateDTO;
import br.com.challenge_java.dto.VeiculoDTO;
import br.com.challenge_java.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoDTO> criarVeiculo(@RequestBody @Valid VeiculoCreateDTO veiculoCreateDTO) {
        VeiculoDTO novoVeiculo = veiculoService.criarVeiculo(veiculoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> buscarVeiculoPorId(@PathVariable Long id) {
        VeiculoDTO veiculoDTO = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(veiculoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<VeiculoDTO>> listarVeiculosPaginados(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<VeiculoDTO> veiculosPage = veiculoService.listarTodosPaginado(pageable);
        return ResponseEntity.ok(veiculosPage);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VeiculoDTO>> listarVeiculosPorUsuario(@PathVariable Long usuarioId) {
        List<VeiculoDTO> veiculos = veiculoService.listarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(veiculos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(@PathVariable Long id, @RequestBody @Valid VeiculoCreateDTO veiculoCreateDTO) {
        VeiculoDTO veiculoAtualizado = veiculoService.atualizarVeiculo(id, veiculoCreateDTO);
        return ResponseEntity.ok(veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        veiculoService.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}