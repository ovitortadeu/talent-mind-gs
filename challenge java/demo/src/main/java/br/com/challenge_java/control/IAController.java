package br.com.challenge_java.control;

import br.com.challenge_java.dto.PlanoEstudoResponse;
import br.com.challenge_java.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IAController {

    private final AIService aiService;

    /**
     * Endpoint para gerar um plano de estudos baseado em uma vaga.
     * Protegido por autenticação (devido ao /api/** no SecurityConfig).
     */
    @GetMapping("/plano-de-estudos/{vagaId}")
    public ResponseEntity<PlanoEstudoResponse> getPlanoDeEstudos(@PathVariable Long vagaId) {
        PlanoEstudoResponse response = aiService.gerarPlanoDeEstudos(vagaId);
        return ResponseEntity.ok(response);
    }
}