package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "T_VAGA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VAGA")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "TITULO_VAGA", length = 150, nullable = false)
    private String titulo;

    @Column(name = "DESCRICAO_VAGA", length = 1000, nullable = false)
    private String descricao;

    @Column(name = "EMPRESA_VAGA", length = 100)
    private String empresa;

    @Column(name = "SALARIO_MEDIO", precision = 10, scale = 2)
    private BigDecimal salarioMedio;

    @CreationTimestamp
    @Column(name = "DT_PUBLICACAO", nullable = false, updatable = false)
    private LocalDate dtPublicacao;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VagaCompetencia> competenciasRequeridas;
}