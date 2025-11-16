package br.com.challenge_java.service;

import br.com.challenge_java.dto.UsuarioCreateDTO;
import br.com.challenge_java.dto.UsuarioDTO;
import br.com.challenge_java.dto.UsuarioUpdateDTO;
import br.com.challenge_java.exception.BusinessException;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.UsuarioMapperInterface; 
import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils; 

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapperInterface usuarioMapper; 
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO registrarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        log.info("Tentativa de registrar novo usuário com username: {}", usuarioCreateDTO.getUsername());

        if (usuarioRepository.findByUsername(usuarioCreateDTO.getUsername()).isPresent()) {
            log.warn("Username '{}' já cadastrado.", usuarioCreateDTO.getUsername());
            throw new BusinessException("Username já cadastrado.");
        }
        if (usuarioRepository.findByEmail(usuarioCreateDTO.getEmail()).isPresent()) {
            log.warn("Email '{}' já cadastrado.", usuarioCreateDTO.getEmail());
            throw new BusinessException("Email já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toUsuario(usuarioCreateDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));

        try {
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            log.info("Usuário '{}' registrado com sucesso com ID: {}", usuarioSalvo.getUsername(), usuarioSalvo.getId());
            return usuarioMapper.toUsuarioDTO(usuarioSalvo); 
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade de dados ao salvar usuário com username '{}': {}", usuarioCreateDTO.getUsername(), e.getMessage(), e);
            throw new BusinessException("Erro ao salvar usuário devido a uma violação de integridade de dados.");
        } catch (Exception e) {
            log.error("Erro inesperado ao salvar usuário com username '{}': {}", usuarioCreateDTO.getUsername(), e.getMessage(), e);
            throw new BusinessException("Ocorreu um erro inesperado ao tentar registrar o usuário.");
        }
    }
    
    @Cacheable(value = "usuarios", key = "#id")
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        log.debug("Buscando usuário com ID: {}", id);
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toUsuarioDTO) 
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listarTodosPaginado(Pageable pageable) {
        log.debug("Listando usuários com paginação: {}", pageable);
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        log.debug("Encontrados {} usuários na página {} de {}. Conteúdo da página: {}",
                usuariosPage.getTotalElements(),
                pageable.getPageNumber(),
                usuariosPage.getTotalPages(),
                usuariosPage.getContent().size());
        return usuariosPage.map(usuarioMapper::toUsuarioDTO);
    }
    
    @Cacheable(value = "usuariosPorUsername", key = "#username")
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarEntidadePorUsername(String username) {
        log.debug("Buscando entidade usuário com username: {}", username);
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (usuarioOptional.isPresent()) {
            log.debug("Entidade usuário encontrada para username: {}", username);
        } else {
            log.debug("Nenhuma entidade usuário encontrada para username: {}", username);
        }
        return usuarioOptional;
    }
    	
   
    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        log.info("Tentativa de atualizar usuário com ID: {}", id);
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado para atualização com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });

        if (StringUtils.hasText(usuarioUpdateDTO.getUsername()) && !usuarioUpdateDTO.getUsername().equals(usuarioExistente.getUsername())) {
            if(usuarioRepository.findByUsername(usuarioUpdateDTO.getUsername()).isPresent()){
                log.warn("Tentativa de atualizar para username '{}' que já existe.", usuarioUpdateDTO.getUsername());
                throw new BusinessException("Username já cadastrado para outro usuário.");
            }
        }
        if (StringUtils.hasText(usuarioUpdateDTO.getEmail()) && !usuarioUpdateDTO.getEmail().equals(usuarioExistente.getEmail())) {
             if(usuarioRepository.findByEmail(usuarioUpdateDTO.getEmail()).isPresent()){
                log.warn("Tentativa de atualizar para email '{}' que já existe.", usuarioUpdateDTO.getEmail());
                throw new BusinessException("Email já cadastrado para outro usuário.");
            }
        }

        usuarioMapper.updateUsuarioFromDto(usuarioUpdateDTO, usuarioExistente);

        if (StringUtils.hasText(usuarioUpdateDTO.getSenha())) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioUpdateDTO.getSenha()));
        }

        try {
            Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
            log.info("Usuário com ID {} atualizado com sucesso.", id);
            return usuarioMapper.toUsuarioDTO(usuarioAtualizado); 
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade de dados ao atualizar usuário com ID '{}': {}", id, e.getMessage(), e);
            throw new BusinessException("Erro ao atualizar usuário devido a uma violação de integridade de dados.");
        }
    }

    @Transactional
    public void deletarUsuario(Long id) {
        log.info("Tentativa de deletar usuário com ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            log.warn("Usuário não encontrado para deleção com ID: {}", id);
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        try {
            usuarioRepository.deleteById(id);
            log.info("Usuário com ID {} deletado com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade de dados ao deletar usuário com ID '{}': {}. Pode haver entidades relacionadas.", id, e.getMessage(), e);
            throw new BusinessException("Não é possível deletar o usuário pois ele possui dados relacionados (ex: veículos).");
        }
    }
}