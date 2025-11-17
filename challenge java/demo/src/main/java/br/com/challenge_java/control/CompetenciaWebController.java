package br.com.challenge_java.control;

import br.com.challenge_java.model.Competencia;
import br.com.challenge_java.repository.CompetenciaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/competencias")
@RequiredArgsConstructor
public class CompetenciaWebController {

    private final CompetenciaRepository competenciaRepository;

    @GetMapping
    public String listarCompetencias(Model model) {
        model.addAttribute("competencias", competenciaRepository.findAll());
        return "competencias/list";
    }

    @GetMapping("/new")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("competencia", new Competencia());
        return "competencias/form";
    }

    @PostMapping("/save")
    public String salvarCompetencia(@Valid @ModelAttribute Competencia competencia,
                                  BindingResult result,
                                  RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            return "competencias/form";
        }

        try {
            if (competencia.getId() == null) {
                competenciaRepository.save(competencia);
                attributes.addFlashAttribute("message", "Competência criada com sucesso!");
            } else {
                competenciaRepository.save(competencia);
                attributes.addFlashAttribute("message", "Competência de ID " + competencia.getId() + " atualizada com sucesso!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao salvar competência: " + e.getMessage());
            return "competencias/form";
        }
        
        return "redirect:/competencias";
    }

    @GetMapping("/edit/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Competencia competencia = competenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Competência inválida Id:" + id));
        model.addAttribute("competencia", competencia);
        return "competencias/form";
    }

    @GetMapping("/delete/{id}")
    public String deletarCompetencia(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            if (competenciaRepository.existsById(id)) {
                competenciaRepository.deleteById(id);
                attributes.addFlashAttribute("message", "Competência de ID " + id + " deletada com sucesso!");
            } else {
                attributes.addFlashAttribute("error", "Competência não encontrada com ID " + id);
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao deletar competência: " + e.getMessage());
        }
        return "redirect:/competencias";
    }
}