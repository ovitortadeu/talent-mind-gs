package br.com.challenge_java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * Chave primária composta para T_USUARIO_COMPETENCIA.
 */
@Embeddable
@Data
@EqualsAndHashCode
public class UsuarioCompetenciaId implements Serializable {
    
    @Column(name = "ID_USUARIO")
    private Long usuarioId;
    
    @Column(name = "ID_COMPETENCIA")
    private Long competenciaId;
}