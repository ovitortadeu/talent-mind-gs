package br.com.challenge_java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraDTO {
    private Long id;
    private String modelo;
    private Long filialId;
}