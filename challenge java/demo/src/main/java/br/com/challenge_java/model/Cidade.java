package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "TB_MTT_CIDADE")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_ESTADO_id", nullable = false)
    private Estado estado;

    @Column(name = "nome_cidade", length = 65, nullable = false)
    private String nomeCidade;

    @Column(name = "numero_ddd", precision = 2, scale = 0, nullable = false)
    private Integer numeroDdd;

    @OneToMany(mappedBy = "cidade", fetch = FetchType.LAZY)
    private List<Logradouro> logradouros;
}