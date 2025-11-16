package br.com.challenge_java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
    private Long id;
    private String siglaEstado;
    private String nomeEstado;
}