package br.com.challenge_java.service;

import br.com.challenge_java.dto.CompetenciaDTO;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.CompetenciaMapper;
import br.com.challenge_java.model.Competencia;
import br.com.challenge_java.repository.CompetenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;
    private final CompetenciaMapper competenciaMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "competenciasPaginadas", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<CompetenciaDTO> listarTodas(Pageable pageable) {
        return competenciaRepository.findAll(pageable)
                .map(competenciaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "competencia", key = "#id")
    public CompetenciaDTO buscarPorId(Long id) {
        return competenciaRepository.findById(id)
                .map(competenciaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Competência não encontrada com ID: " + id));
    }

    @Transactional
    @CacheEvict(value = {"competencia", "competenciasPaginadas"}, allEntries = true)
    public CompetenciaDTO criar(CompetenciaDTO dto) {
        Competencia entity = competenciaMapper.toEntity(dto);
        Competencia salva = competenciaRepository.save(entity);
        return competenciaMapper.toDTO(salva);
    }

    @Transactional
    @CacheEvict(value = {"competencia", "competenciasPaginadas"}, allEntries = true)
    public CompetenciaDTO atualizar(Long id, CompetenciaDTO dto) {
        Competencia entity = competenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competência não encontrada com ID: " + id));
        
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        
        Competencia atualizada = competenciaRepository.save(entity);
        return competenciaMapper.toDTO(atualizada);
    }

    @Transactional
    @CacheEvict(value = {"competencia", "competenciasPaginadas"}, allEntries = true)
    public void deletar(Long id) {
        if (!competenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Competência não encontrada com ID: " + id);
        }
        competenciaRepository.deleteById(id);
    }
}