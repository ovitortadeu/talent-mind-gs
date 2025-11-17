package br.com.challenge_java.service;

import br.com.challenge_java.dto.PlanoEstudoResponse;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.model.CursoCompetencia;
import br.com.challenge_java.model.Vaga;
import br.com.challenge_java.model.VagaCompetencia;
import br.com.challenge_java.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {

    private static final Logger log = LoggerFactory.getLogger(AIService.class);
    private final VagaRepository vagaRepository;
    private final ChatClient chatClient; // Injetando o ChatClient configurado

    /**
     * Gera um plano de estudos usando IA generativa (Ollama)
     * com base nas competências de uma vaga e nos cursos disponíveis.
     */
    public PlanoEstudoResponse gerarPlanoDeEstudos(Long vagaId) {
        log.info("Iniciando geração de plano de estudos para Vaga ID: {}", vagaId);

        Vaga vaga = vagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com ID: " + vagaId));

        // 1. Busca as competências que a vaga exige
        String competencias = vaga.getCompetenciasRequeridas().stream()
                .map(compReq -> compReq.getCompetencia().getNome())
                .collect(Collectors.joining(", "));

        // 2. Busca os cursos que ensinam essas competências
        String cursos = vaga.getCompetenciasRequeridas().stream()
                .flatMap(compReq -> compReq.getCompetencia().getCursos().stream()) // Navega: Vaga -> Competencia -> Cursos
                .map(CursoCompetencia::getCurso)
                .distinct() // Remove cursos duplicados
                .map(curso -> curso.getNome() + " (Instituição: " + curso.getInstituicao() + ")")
                .collect(Collectors.joining("; "));

        log.info("Competências encontradas: {}", competencias);
        log.info("Cursos relacionados encontrados: {}", cursos);

        // 3. Define o Template do Prompt
        String promptTemplate = """
            Você é um "Assistente de Carreira" sênior da TalentMind.
            Sua missão é criar um plano de estudos simples e motivacional para um usuário que deseja se aplicar à seguinte vaga:
            
            Vaga: {vagaTitulo}
            Descrição: {vagaDescricao}
            Competências Exigidas: {competencias}

            Para ajudar, a plataforma TalentMind identificou os seguintes cursos em nosso banco de dados que cobrem essas competências:
            Cursos Disponíveis: {cursos}

            Por favor, gere um plano de estudos de 3 passos, em português do Brasil,
            sugerindo como o usuário pode usar os cursos disponíveis para 
            se qualificar para a vaga. Seja encorajador e profissional.
            Se nenhum curso for encontrado, sugira que o usuário busque ativamente por essas competências.
        """;

        // 4. Preenche o template
        Map<String, Object> promptParams = Map.of(
            "vagaTitulo", vaga.getTitulo(),
            "vagaDescricao", vaga.getDescricao(),
            "competencias", competencias.isEmpty() ? "Nenhuma competência específica listada" : competencias,
            "cursos", cursos.isEmpty() ? "Nenhum curso diretamente relacionado encontrado em nossa base." : cursos
        );

        Prompt prompt = new PromptTemplate(promptTemplate).create(promptParams);

        // 5. Chama a IA (Ollama)
        log.info("Enviando prompt para o Ollama (llama3:8b)...");
        String resposta = chatClient.prompt(prompt)
                                    .call()
                                    .content(); // Extrai o texto da resposta
        
        log.info("Resposta da IA recebida.");
        return new PlanoEstudoResponse(resposta);
    }
}