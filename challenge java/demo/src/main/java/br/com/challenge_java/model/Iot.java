package br.com.challenge_java.model;

import br.com.challenge_java.model.enuns.IotStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TB_MTT_IOT")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Iot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_VEICULO_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "status", nullable = false)
    private IotStatus status;

    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;
}