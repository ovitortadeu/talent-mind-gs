package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.VeiculoCreateDTO;
import br.com.challenge_java.dto.VeiculoDTO;
import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.model.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VeiculoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.username", target = "usernameUsuario")
    VeiculoDTO toVeiculoDTO(Veiculo veiculo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "usuarioFromId")
    Veiculo toVeiculo(VeiculoCreateDTO veiculoCreateDTO);

    @Named("usuarioFromId")
    default Usuario usuarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}