package br.com.challenge_java.control;

import br.com.challenge_java.dto.VagaDTO; 
import br.com.challenge_java.mapper.VagaMapper;
import br.com.challenge_java.model.Vaga;
import br.com.challenge_java.repository.VagaRepository;
import br.com.challenge_java.service.VagaService; 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaWebController {

    private final VagaRepository vagaRepository; 
    private final VagaService vagaService;
    private final VagaMapper vagaMapper;

    @GetMapping
    public String listarVagas(Model model) {
        model.addAttribute("vagas", vagaRepository.findAll());
        return "vagas/list";
    }

    @GetMapping("/new")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("vaga", new Vaga());
        return "vagas/form";
    }

    @PostMapping("/save")
    public String salvarVaga(@Valid @ModelAttribute("vaga") Vaga vaga,
                           BindingResult result,
                           RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            return "vagas/form";
        }

        try {
            VagaDTO vagaDTO = vagaMapper.toDTO(vaga);
            
            if (vaga.getId() == null) {
                vagaService.criar(vagaDTO); 
                attributes.addFlashAttribute("message", "Vaga criada com sucesso!");
            } else {
                vagaService.atualizar(vaga.getId(), vagaDTO);
                attributes.addFlashAttribute("message", "Vaga de ID " + vaga.getId() + " atualizada com sucesso!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao salvar vaga: " + e.getMessage());
            return "vagas/form";
        }
        
        return "redirect:/vagas";
    }

    @GetMapping("/edit/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaga inválida Id:" + id));
        model.addAttribute("vaga", vaga);
        return "vagas/form";
    }

    @GetMapping("/delete/{id}")
    public String deletarVaga(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            vagaService.deletar(id);
            attributes.addFlashAttribute("message", "Vaga de ID " + id + " deletada com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao deletar vaga: " + e.getMessage());
        }
        return "redirect:/vagas";
    }
}