package br.com.challenge_java.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VeiculoCreateDTO {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @Size(max = 7, message = "Placa antiga deve ter no máximo 7 caracteres")
    private String placaAntiga;

    @NotBlank(message = "Placa nova é obrigatória")
    @Size(min = 7, max = 10, message = "Placa nova deve ter entre 7 e 10 caracteres") 
    private String placaNova;

    @NotBlank(message = "Tipo do veículo é obrigatório")
    @Size(max = 75, message = "Tipo do veículo não pode exceder 75 caracteres")
    private String tipoVeiculo;
}