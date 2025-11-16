package br.com.challenge_java.service;

import br.com.challenge_java.dto.EstadoDTO;
import br.com.challenge_java.mapper.EstadoMapper;
import br.com.challenge_java.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoService {
    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    @Transactional(readOnly = true)
    public List<EstadoDTO> listarTodos() {
        return estadoRepository.findAll().stream()
                .map(estadoMapper::toEstadoDTO)
                .collect(Collectors.toList());
    }
}