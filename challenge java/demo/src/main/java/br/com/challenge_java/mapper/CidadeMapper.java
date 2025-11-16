package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.CidadeDTO;
import br.com.challenge_java.model.Cidade;
import br.com.challenge_java.model.Estado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CidadeMapper {

    @Mapping(source = "estado.id", target = "estadoId")
    @Mapping(source = "estado.nomeEstado", target = "nomeEstado")
    CidadeDTO toCidadeDTO(Cidade cidade);

    @Mapping(source = "estadoId", target = "estado", qualifiedByName = "estadoFromId")
    Cidade toCidade(CidadeDTO cidadeDTO);

    @Named("estadoFromId")
    default Estado estadoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Estado estado = new Estado();
        estado.setId(id);
        return estado;
    }
}