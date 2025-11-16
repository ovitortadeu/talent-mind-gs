package br.com.challenge_java.control;

import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard"); // Para o layout
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalVeiculos", veiculoRepository.count());
        return "dashboard";
    }
}