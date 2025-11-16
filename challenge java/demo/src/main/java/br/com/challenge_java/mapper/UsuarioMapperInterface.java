package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.UsuarioCreateDTO;
import br.com.challenge_java.dto.UsuarioDTO;
import br.com.challenge_java.dto.UsuarioUpdateDTO;
import br.com.challenge_java.model.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapperInterface {

    UsuarioDTO toUsuarioDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "veiculos", ignore = true)
    Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "veiculos", ignore = true)
    @Mapping(target = "senha", ignore = true) 
    void updateUsuarioFromDto(UsuarioUpdateDTO usuarioUpdateDTO, @MappingTarget Usuario usuario);
}