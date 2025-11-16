package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.FilialDTO;
import br.com.challenge_java.model.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FilialMapper {
    FilialDTO toFilialDTO(Filial filial);
    Filial toFilial(FilialDTO filialDTO);
}