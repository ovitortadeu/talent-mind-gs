package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "TB_MTT_ESTADO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "sigla_estado", length = 2, nullable = false)
    private String siglaEstado;

    @Column(name = "nome_estado", length = 25, nullable = false)
    private String nomeEstado;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    private List<Cidade> cidades;
}