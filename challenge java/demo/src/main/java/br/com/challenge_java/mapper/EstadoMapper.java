package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.EstadoDTO;
import br.com.challenge_java.model.Estado;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstadoMapper {
    EstadoDTO toEstadoDTO(Estado estado);
    Estado toEstado(EstadoDTO estadoDTO);
}