package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.VagaDTO;
import br.com.challenge_java.model.Vaga;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VagaMapper {

    VagaDTO toDTO(Vaga vaga);
    
    Vaga toEntity(VagaDTO vagaDTO);
}