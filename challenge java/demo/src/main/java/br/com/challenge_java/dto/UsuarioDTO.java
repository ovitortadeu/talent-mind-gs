package br.com.challenge_java.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Long id;
    private String username;
    private String email;
}