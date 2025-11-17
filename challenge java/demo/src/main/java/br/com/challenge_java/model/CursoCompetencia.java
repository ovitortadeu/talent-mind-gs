package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "T_CURSO_COMPETENCIA")
@Data
public class CursoCompetencia {

    @EmbeddedId
    private CursoCompetenciaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cursoId")
    @JoinColumn(name = "ID_CURSO")
    private CursoRequalificacao curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("competenciaId")
    @JoinColumn(name = "ID_COMPETENCIA")
    private Competencia competencia;
}