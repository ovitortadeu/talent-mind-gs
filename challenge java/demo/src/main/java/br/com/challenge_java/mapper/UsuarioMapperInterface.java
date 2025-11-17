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

    // Mapeia a entidade Usuario para o DTO de resposta
    UsuarioDTO toUsuarioDTO(Usuario usuario);

    // Mapeia o DTO de criação para a entidade Usuario
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dtCadastro", ignore = true)
    @Mapping(target = "competencias", ignore = true)
    @Mapping(target = "role", ignore = true) // O Role será definido no Service
    Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO);

    // Atualiza a entidade Usuario a partir do DTO de atualização
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dtCadastro", ignore = true)
    @Mapping(target = "competencias", ignore = true)
    @Mapping(target = "senha", ignore = true) // Senha é tratada separadamente no Service
    @Mapping(target = "role", ignore = true)
    void updateUsuarioFromDto(UsuarioUpdateDTO usuarioUpdateDTO, @MappingTarget Usuario usuario);
}