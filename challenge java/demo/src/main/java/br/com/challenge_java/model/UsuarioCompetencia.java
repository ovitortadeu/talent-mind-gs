package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
// A classe 'UsuarioCompetenciaId' foi movida para seu próprio arquivo.

@Entity
@Table(name = "T_USUARIO_COMPETENCIA")
@Data
public class UsuarioCompetencia {

    @EmbeddedId
    private UsuarioCompetenciaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId") 
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("competenciaId") 
    @JoinColumn(name = "ID_COMPETENCIA")
    private Competencia competencia;

    @Column(name = "NIVEL_COMPETENCIA", length = 50)
    private String nivel;
}