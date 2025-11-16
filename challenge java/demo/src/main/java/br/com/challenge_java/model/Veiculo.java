package br.com.challenge_java.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "TB_MTT_VEICULO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Exclude
    @NotNull(message = "O proprietário é obrigatório.") 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_MTT_USUARIO_id", nullable = false)
    private Usuario usuario;

    @Column(name = "placa_antiga", length = 7)
    private String placaAntiga;
    
    @NotBlank(message = "A placa nova é obrigatória.")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres.")
    @Column(name = "placa_nova", length = 10, nullable = false, unique = true)
    private String placaNova;
    
    @NotBlank(message = "O tipo do veículo é obrigatório.")
    @Column(name = "tipo_veiculo", length = 75, nullable = false)
    private String tipoVeiculo;

    @ToString.Exclude
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Iot> dispositivosIot;
}