package br.com.challenge_java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogradouroDTO {
    private Long id;
    private String nomeLogradouro;
    private String numeroLogradouro;
    private String cep;
    private String complemento;
    private Long cidadeId;
    private String nomeCidade; 
    private Long usuarioId;
    private Long filialId; 
}