package br.com.challenge_java.service;

import br.com.challenge_java.config.messaging.RabbitMQConfig;
import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.model.UsuarioCompetencia;
import br.com.challenge_java.model.Vaga;
import br.com.challenge_java.model.VagaCompetencia;
import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço assíncrono para processar cálculos de compatibilidade
 * ouvindo as filas do RabbitMQ.
 */
@Service
@RequiredArgsConstructor
public class CompatibilidadeService {

    private static final Logger log = LoggerFactory.getLogger(CompatibilidadeService.class);

    private final VagaRepository vagaRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Ouve a fila de novas vagas e processa a compatibilidade para todos os usuários.
     * @param vagaId O ID da vaga enviado pela fila.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_VAGA_NOVA)
    @Transactional(readOnly = true)
    public void processarCompatibilidadeNovaVaga(Long vagaId) {
        log.warn("MENSAGEM RECEBIDA (Assíncrono) - Vaga ID: {}. Iniciando cálculo de compatibilidade...", vagaId);
        
        try {
            Thread.sleep(2000); 

            Vaga vaga = vagaRepository.findById(vagaId)
                    .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada na fila: " + vagaId));
            
            Set<Long> competenciasRequeridasIds = vaga.getCompetenciasRequeridas().stream()
                    .map(VagaCompetencia::getCompetencia)
                    .map(comp -> comp.getId())
                    .collect(Collectors.toSet());

            if (competenciasRequeridasIds.isEmpty()) {
                log.info("[Vaga ID: {}] - Vaga não exige competências. Compatibilidade de 100% para todos.", vagaId);
                return;
            }

            int totalRequisitos = competenciasRequeridasIds.size();
            List<Usuario> usuarios = usuarioRepository.findAll();

            log.info("[Vaga ID: {}] - Verificando {} usuários contra {} competências requeridas.", 
                     vagaId, usuarios.size(), totalRequisitos);

            for (Usuario usuario : usuarios) {
                Set<Long> competenciasUsuarioIds = usuario.getCompetencias().stream()
                        .map(UsuarioCompetencia::getCompetencia)
                        .map(comp -> comp.getId())
                        .collect(Collectors.toSet());
                
                long compativeis = competenciasUsuarioIds.stream()
                        .filter(id -> competenciasRequeridasIds.contains(id))
                        .count();
                
                double percentual = (double) compativeis / totalRequisitos * 100.0;
                
                log.info("-> Usuário [ID: {}] | Vaga [ID: {}] | Compatibilidade: {}/{} = {:.2f}%", 
                         usuario.getId(), vagaId, compativeis, totalRequisitos, percentual);
            }
            log.warn("MENSAGEM PROCESSADA (Assíncrono) - Vaga ID: {}. Cálculo finalizado.", vagaId);

        } catch (Exception e) {
            log.error("ERRO AO PROCESSAR MENSAGEM da Vaga ID {}: {}", vagaId, e.getMessage());
        }
    }
}