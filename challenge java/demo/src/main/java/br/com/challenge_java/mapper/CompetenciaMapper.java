package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.CompetenciaDTO;
import br.com.challenge_java.model.Competencia;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetenciaMapper {
    
    CompetenciaDTO toDTO(Competencia competencia);
    
    Competencia toEntity(CompetenciaDTO competenciaDTO);
}