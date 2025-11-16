package br.com.challenge_java.dto;

import br.com.challenge_java.model.enuns.IotStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IotDTO {
    private Long id;
    private IotStatus status;
    private String tipo;
    private Long veiculoId;
}