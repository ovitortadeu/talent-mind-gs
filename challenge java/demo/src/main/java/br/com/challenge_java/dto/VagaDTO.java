package br.com.challenge_java.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para a entidade Vaga.
 */
@Data
public class VagaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String empresa;
    private BigDecimal salarioMedio;
    private LocalDate dtPublicacao;
}