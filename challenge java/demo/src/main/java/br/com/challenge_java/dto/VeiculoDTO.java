package br.com.challenge_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {
    private Long id;
    private Long usuarioId;
    private String usernameUsuario;
    private String placaAntiga;
    private String placaNova;
    private String tipoVeiculo;
}