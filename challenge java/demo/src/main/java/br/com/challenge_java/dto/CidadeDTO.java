package br.com.challenge_java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {
    private Long id;
    private String nomeCidade;
    private Integer numeroDdd;
    private Long estadoId; 
    private String nomeEstado; 
}