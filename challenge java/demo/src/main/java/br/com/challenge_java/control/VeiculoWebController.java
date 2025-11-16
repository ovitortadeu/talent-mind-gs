package br.com.challenge_java.control;

import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.model.Veiculo;
import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoWebController {

    private final VeiculoService veiculoService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", veiculoService.buscarVeiculosPorPerfil());
        return "veiculos/list";
    }

    @GetMapping("/new")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        model.addAttribute("todosUsuarios", usuarioRepository.findAll());
        return "veiculos/form";
    }

    @PostMapping("/save")
    public String salvarVeiculo(@Valid @ModelAttribute("veiculo") Veiculo veiculo,
                              BindingResult result,
                              Model model, 
                              RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("todosUsuarios", usuarioRepository.findAll());
            return "veiculos/form";
        }

        if (veiculo.getId() == null) {
            veiculoService.salvar(veiculo);
            attributes.addFlashAttribute("message", "Veículo criado com sucesso!");
        } else {
            veiculoService.atualizar(veiculo.getId(), veiculo);
            attributes.addFlashAttribute("message", "Veículo de ID " + veiculo.getId() + " atualizado com sucesso!");
        }
        
        return "redirect:/veiculos";
    }

    @GetMapping("/edit/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("veiculo", veiculoService.findEntityById(id));
        model.addAttribute("todosUsuarios", usuarioRepository.findAll());
        return "veiculos/form";
    }

    @GetMapping("/delete/{id}")
    public String deletarVeiculo(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            veiculoService.deletarVeiculo(id);
            attributes.addFlashAttribute("message", "Veículo de ID " + id + " deletado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao deletar veículo: " + e.getMessage());
        }
        return "redirect:/veiculos";
    }
}