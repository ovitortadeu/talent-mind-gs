package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.CursoRequalificacaoDTO;
import br.com.challenge_java.model.CursoRequalificacao;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CursoRequalificacaoMapper {
    
    CursoRequalificacaoDTO toDTO(CursoRequalificacao curso);
    
    CursoRequalificacao toEntity(CursoRequalificacaoDTO cursoDTO);
}