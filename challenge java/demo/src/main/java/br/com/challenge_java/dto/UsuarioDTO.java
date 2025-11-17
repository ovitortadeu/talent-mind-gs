package br.com.challenge_java.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO para exibir informações de um Usuário.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Long id;
    private String nomeUsuario;
    private String email;
}