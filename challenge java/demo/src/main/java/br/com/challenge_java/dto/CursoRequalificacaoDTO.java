package br.com.challenge_java.dto;

import lombok.Data;

/**
 * DTO para a entidade CursoRequalificacao.
 */
@Data
public class CursoRequalificacaoDTO {
    private Long id;
    private String nome;
    private String instituicao;
    private String link;
}