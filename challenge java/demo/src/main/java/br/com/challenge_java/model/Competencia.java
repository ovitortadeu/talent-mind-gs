package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "T_COMPETENCIA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPETENCIA")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME_COMPETENCIA", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name = "DESCRICAO_COMP", length = 300)
    private String descricao;

    @OneToMany(mappedBy = "competencia")
    private List<UsuarioCompetencia> usuarios;

    @OneToMany(mappedBy = "competencia")
    private List<VagaCompetencia> vagas;

    @OneToMany(mappedBy = "competencia")
    private List<CursoCompetencia> cursos;
}