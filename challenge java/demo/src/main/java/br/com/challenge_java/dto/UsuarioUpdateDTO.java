package br.com.challenge_java.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String username; 

    @Email(message = "Email deve ser válido")
    private String email; 

    @Size(min = 6, max = 100, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha; 
}
