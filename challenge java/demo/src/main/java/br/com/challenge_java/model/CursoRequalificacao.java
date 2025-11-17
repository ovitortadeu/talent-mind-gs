package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "T_CURSO_REQUALIFICACAO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CursoRequalificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME_CURSO", length = 200, nullable = false)
    private String nome;

    @Column(name = "INSTITUICAO_CURSO", length = 100)
    private String instituicao;

    @Column(name = "LINK_CURSO", length = 255)
    private String link;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoCompetencia> competenciasAbordadas;
}