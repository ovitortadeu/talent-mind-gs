package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "TB_MTT_FILIAL")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Camera> cameras;

    @OneToOne(mappedBy = "filial", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Logradouro logradouro;
}