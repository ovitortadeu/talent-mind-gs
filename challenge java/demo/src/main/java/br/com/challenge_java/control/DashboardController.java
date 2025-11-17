package br.com.challenge_java.control;

import br.com.challenge_java.repository.CompetenciaRepository;
import br.com.challenge_java.repository.CursoRequalificacaoRepository;
import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    private final UsuarioRepository usuarioRepository;
    private final VagaRepository vagaRepository;
    private final CompetenciaRepository competenciaRepository;
    private final CursoRequalificacaoRepository cursoRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Locale locale) {
        
        log.info("--- Carregando Dashboard. O Locale atual para este usuário é: {} ---", locale);

        model.addAttribute("pageTitle", "Dashboard"); // Para o layout
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalVagas", vagaRepository.count());
        model.addAttribute("totalCompetencias", competenciaRepository.count());
        model.addAttribute("totalCursos", cursoRepository.count());
        return "dashboard";
    }
}