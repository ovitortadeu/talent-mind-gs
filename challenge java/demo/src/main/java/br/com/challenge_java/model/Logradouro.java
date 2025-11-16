package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TB_MTT_LOGRADOURO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Logradouro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_USUARIO_id", unique = true)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_FILIAL_id", unique = true)
    private Filial filial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_CIDADE_id", nullable = false)
    private Cidade cidade;

    @Column(name = "nome_logradouro", length = 100, nullable = false)
    private String nomeLogradouro;

    @Column(name = "numero_logradouro", length = 10, nullable = false)
    private String numeroLogradouro;

    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @Column(name = "complemento", length = 50)
    private String complemento;
}