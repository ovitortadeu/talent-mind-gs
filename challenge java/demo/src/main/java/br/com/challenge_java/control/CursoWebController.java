package br.com.challenge_java.control;

import br.com.challenge_java.model.CursoRequalificacao;
import br.com.challenge_java.repository.CursoRequalificacaoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoWebController {

    private final CursoRequalificacaoRepository cursoRepository;

    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        return "cursos/list";
    }

    @GetMapping("/new")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("curso", new CursoRequalificacao());
        return "cursos/form";
    }

    @PostMapping("/save")
    public String salvarCurso(@Valid @ModelAttribute CursoRequalificacao curso,
                              BindingResult result,
                              RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            return "cursos/form";
        }

        try {
            if (curso.getId() == null) {
                cursoRepository.save(curso);
                attributes.addFlashAttribute("message", "Curso criado com sucesso!");
            } else {
                cursoRepository.save(curso);
                attributes.addFlashAttribute("message", "Curso de ID " + curso.getId() + " atualizado com sucesso!");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao salvar curso: " + e.getMessage());
            return "cursos/form";
        }
        
        return "redirect:/cursos";
    }

    @GetMapping("/edit/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        CursoRequalificacao curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso inválido Id:" + id));
        model.addAttribute("curso", curso);
        return "cursos/form";
    }

    @GetMapping("/delete/{id}")
    public String deletarCurso(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            if (cursoRepository.existsById(id)) {
                cursoRepository.deleteById(id);
                attributes.addFlashAttribute("message", "Curso de ID " + id + " deletado com sucesso!");
            } else {
                attributes.addFlashAttribute("error", "Curso não encontrado com ID " + id);
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao deletar curso: " + e.getMessage());
        }
        return "redirect:/cursos";
    }
}