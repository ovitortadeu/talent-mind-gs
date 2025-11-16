package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.LogradouroDTO;
import br.com.challenge_java.model.Cidade;
import br.com.challenge_java.model.Filial;
import br.com.challenge_java.model.Logradouro;
import br.com.challenge_java.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CidadeMapper.class, UsuarioMapperInterface.class, FilialMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogradouroMapper {

    @Mapping(source = "cidade.id", target = "cidadeId")
    @Mapping(source = "cidade.nomeCidade", target = "nomeCidade")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "filial.id", target = "filialId")
    LogradouroDTO toLogradouroDTO(Logradouro logradouro);

    @Mapping(source = "cidadeId", target = "cidade", qualifiedByName = "cidadeFromId")
    @Mapping(source = "usuarioId", target = "usuario", qualifiedByName = "usuarioFromId")
    @Mapping(source = "filialId", target = "filial", qualifiedByName = "filialFromIdLogradouro")
    Logradouro toLogradouro(LogradouroDTO logradouroDTO);

    @Named("cidadeFromId")
    default Cidade cidadeFromId(Long id) {
        if (id == null) return null;
        Cidade cidade = new Cidade();
        cidade.setId(id);
        return cidade;
    }

    @Named("usuarioFromId")
    default Usuario usuarioFromId(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Named("filialFromIdLogradouro") 
    default Filial filialFromIdLogradouro(Long id) {
        if (id == null) return null;
        Filial filial = new Filial();
        filial.setId(id);
        return filial;
    }
}