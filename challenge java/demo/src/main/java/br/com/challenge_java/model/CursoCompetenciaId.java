package br.com.challenge_java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * Chave primária composta para T_CURSO_COMPETENCIA.
 */
@Embeddable
@Data
@EqualsAndHashCode
public class CursoCompetenciaId implements Serializable {
    
    @Column(name = "ID_CURSO")
    private Long cursoId;
    
    @Column(name = "ID_COMPETENCIA")
    private Long competenciaId;
}