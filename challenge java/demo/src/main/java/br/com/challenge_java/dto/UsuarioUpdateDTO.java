package br.com.challenge_java.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para atualização parcial de um Usuário.
 */
@Data
public class UsuarioUpdateDTO {
    
    @Size(min = 3, max = 150, message = "Nome do usuário deve ter entre 3 e 150 caracteres")
    private String nomeUsuario; 

    @Email(message = "Email deve ser válido")
    private String email; 

    @Size(min = 6, max = 100, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha; 
}