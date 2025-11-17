package br.com.challenge_java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * Chave primária composta para T_VAGA_COMPETENCIA.
 */
@Embeddable
@Data
@EqualsAndHashCode
public class VagaCompetenciaId implements Serializable {
    
    @Column(name = "ID_VAGA")
    private Long vagaId;
    
    @Column(name = "ID_COMPETENCIA")
    private Long competenciaId;
}