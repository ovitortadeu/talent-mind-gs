package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
// A classe 'VagaCompetenciaId' foi movida para seu próprio arquivo.

@Entity
@Table(name = "T_VAGA_COMPETENCIA")
@Data
public class VagaCompetencia {

    @EmbeddedId
    private VagaCompetenciaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vagaId")
    @JoinColumn(name = "ID_VAGA")
    private Vaga vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("competenciaId")
    @JoinColumn(name = "ID_COMPETENCIA")
    private Competencia competencia;
}