package br.com.challenge_java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

 @Entity
 @Table(name = "TB_MTT_CAMERA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_FILIAL_id", nullable = false)
    private Filial filial;

    @Column(name = "modelo", length = 40, nullable = false)
    private String modelo;
}