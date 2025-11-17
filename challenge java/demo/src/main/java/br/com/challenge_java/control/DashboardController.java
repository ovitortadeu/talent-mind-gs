package br.com.challenge_java.control;

import br.com.challenge_java.repository.CompetenciaRepository;
import br.com.challenge_java.repository.CursoRequalificacaoRepository;
import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final VagaRepository vagaRepository;
    private final CompetenciaRepository competenciaRepository;
    private final CursoRequalificacaoRepository cursoRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard"); // Para o layout
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalVagas", vagaRepository.count());
        model.addAttribute("totalCompetencias", competenciaRepository.count());
        model.addAttribute("totalCursos", cursoRepository.count());
        return "dashboard";
    }
}