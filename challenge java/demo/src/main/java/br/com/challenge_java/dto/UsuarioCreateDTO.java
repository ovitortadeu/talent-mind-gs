package br.com.challenge_java.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para criação de um novo Usuário.
 */
@Data
public class UsuarioCreateDTO {
    
    @NotBlank(message = "Nome do usuário é obrigatório")
    @Size(min = 3, max = 150, message = "Nome do usuário deve ter entre 3 e 150 caracteres")
    private String nomeUsuario;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;
}