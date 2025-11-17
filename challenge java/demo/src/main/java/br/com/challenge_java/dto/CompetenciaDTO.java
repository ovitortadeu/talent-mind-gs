package br.com.challenge_java.dto;

import lombok.Data;

/**
 * DTO para a entidade Competencia.
 */
@Data
public class CompetenciaDTO {
    private Long id;
    private String nome;
    private String descricao;
}