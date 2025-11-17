package br.com.challenge_java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.challenge_java.config.messaging.RabbitMQConfig;
import br.com.challenge_java.dto.VagaDTO;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.VagaMapper;
import br.com.challenge_java.model.Vaga;
import br.com.challenge_java.repository.VagaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VagaService {

    private static final Logger log = LoggerFactory.getLogger(VagaService.class);

    private final VagaRepository vagaRepository;
    private final VagaMapper vagaMapper;
    private final RabbitTemplate rabbitTemplate;

    @Transactional(readOnly = true)
    @Cacheable(value = "vagasPaginadas", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<VagaDTO> listarTodas(Pageable pageable) {
        return vagaRepository.findAll(pageable)
                .map(vagaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "vaga", key = "#id")
    public VagaDTO buscarPorId(Long id) {
        return vagaRepository.findById(id)
                .map(vagaMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com ID: " + id));
    }

    @Transactional
    @CacheEvict(value = {"vaga", "vagasPaginadas"}, allEntries = true)
    public VagaDTO criar(VagaDTO dto) {
        Vaga entity = vagaMapper.toEntity(dto);
        Vaga salva = vagaRepository.save(entity);
        log.info("Vaga ID {} salva no banco de dados.", salva.getId());

        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_TALENTMIND,     
                RabbitMQConfig.ROUTING_KEY_VAGA_NOVA,
                salva.getId()                       
            );
            log.info("Mensagem com Vaga ID {} enviada para a fila [{}].", salva.getId(), RabbitMQConfig.QUEUE_VAGA_NOVA);
        } catch (Exception e) {
            log.error("FALHA AO ENVIAR MENSAGEM para a fila da Vaga ID {}: {}", salva.getId(), e.getMessage());
        }

        return vagaMapper.toDTO(salva);
    }

    @Transactional
    @CacheEvict(value = {"vaga", "vagasPaginadas"}, allEntries = true)
    public VagaDTO atualizar(Long id, VagaDTO dto) {
        Vaga entity = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com ID: " + id));
        
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setEmpresa(dto.getEmpresa());
        entity.setSalarioMedio(dto.getSalarioMedio());
        
        Vaga atualizada = vagaRepository.save(entity);
        return vagaMapper.toDTO(atualizada);
    }

    @Transactional
    @CacheEvict(value = {"vaga", "vagasPaginadas"}, allEntries = true)
    public void deletar(Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vaga não encontrada com ID: " + id);
        }
        vagaRepository.deleteById(id);
    }
}