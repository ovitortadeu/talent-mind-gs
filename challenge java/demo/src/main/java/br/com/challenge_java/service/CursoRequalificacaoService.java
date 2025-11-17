package br.com.challenge_java.service;

import br.com.challenge_java.dto.CursoRequalificacaoDTO;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.CursoRequalificacaoMapper;
import br.com.challenge_java.model.CursoRequalificacao;
import br.com.challenge_java.repository.CursoRequalificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CursoRequalificacaoService {

    private final CursoRequalificacaoRepository cursoRepository;
    private final CursoRequalificacaoMapper cursoMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "cursosPaginados", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<CursoRequalificacaoDTO> listarTodos(Pageable pageable) {
        return cursoRepository.findAll(pageable)
                .map(cursoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "curso", key = "#id")
    public CursoRequalificacaoDTO buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .map(cursoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + id));
    }

    @Transactional
    @CacheEvict(value = {"curso", "cursosPaginados"}, allEntries = true)
    public CursoRequalificacaoDTO criar(CursoRequalificacaoDTO dto) {
        CursoRequalificacao entity = cursoMapper.toEntity(dto);
        CursoRequalificacao salvo = cursoRepository.save(entity);
        return cursoMapper.toDTO(salvo);
    }

    @Transactional
    @CacheEvict(value = {"curso", "cursosPaginados"}, allEntries = true)
    public CursoRequalificacaoDTO atualizar(Long id, CursoRequalificacaoDTO dto) {
        CursoRequalificacao entity = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + id));
        
        entity.setNome(dto.getNome());
        entity.setInstituicao(dto.getInstituicao());
        entity.setLink(dto.getLink());
        
        CursoRequalificacao atualizado = cursoRepository.save(entity);
        return cursoMapper.toDTO(atualizado);
    }

    @Transactional
    @CacheEvict(value = {"curso", "cursosPaginados"}, allEntries = true)
    public void deletar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso não encontrado com ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
}