package br.com.challenge_java.service;

import br.com.challenge_java.dto.UsuarioCreateDTO;
import br.com.challenge_java.dto.UsuarioDTO;
import br.com.challenge_java.dto.UsuarioUpdateDTO;
import br.com.challenge_java.exception.BusinessException;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.UsuarioMapperInterface; 
import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.model.enuns.Role; // Importando o Role
import br.com.challenge_java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
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

    /**
     * Registra um novo usuário no sistema.
     * @param usuarioCreateDTO DTO com os dados de criação.
     * @return UsuarioDTO do usuário recém-criado.
     */
    @Transactional
    public UsuarioDTO registrarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        log.info("Tentativa de registrar novo usuário com email: {}", usuarioCreateDTO.getEmail());

        // Validação de email duplicado
        if (usuarioRepository.findByEmail(usuarioCreateDTO.getEmail()).isPresent()) {
            log.warn("Email '{}' já cadastrado.", usuarioCreateDTO.getEmail());
            throw new BusinessException("Email já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toUsuario(usuarioCreateDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuario.setRole(Role.USER); // Define o papel padrão para novos usuários

        try {
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            log.info("Usuário '{}' (ID: {}) registrado com sucesso.", usuarioSalvo.getNomeUsuario(), usuarioSalvo.getId());
            return usuarioMapper.toUsuarioDTO(usuarioSalvo); 
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade de dados ao salvar usuário com email '{}': {}", usuarioCreateDTO.getEmail(), e.getMessage(), e);
            throw new BusinessException("Erro ao salvar usuário devido a uma violação de integridade de dados.");
        } catch (Exception e) {
            log.error("Erro inesperado ao salvar usuário com email '{}': {}", usuarioCreateDTO.getEmail(), e.getMessage(), e);
            throw new BusinessException("Ocorreu um erro inesperado ao tentar registrar o usuário.");
        }
    }
    
    /**
     * Busca um usuário pelo ID.
     * @param id O ID do usuário.
     * @return UsuarioDTO.
     */
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

    /**
     * Lista todos os usuários de forma paginada.
     * @param pageable Configuração da paginação.
     * @return Página de UsuarioDTO.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listarTodosPaginado(Pageable pageable) {
        log.debug("Listando usuários com paginação: {}", pageable);
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return usuariosPage.map(usuarioMapper::toUsuarioDTO);
    }
    
    /**
     * Busca a entidade usuário pelo email.
     * @param email Email do usuário.
     * @return Optional<Usuario>
     */
    @Cacheable(value = "usuariosPorEmail", key = "#email")
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarEntidadePorEmail(String email) {
        log.debug("Buscando entidade usuário com email: {}", email);
        return usuarioRepository.findByEmail(email);
    }
    	
   
    /**
     * Atualiza um usuário existente.
     * @param id ID do usuário a ser atualizado.
     * @param usuarioUpdateDTO DTO com os dados de atualização.
     * @return UsuarioDTO atualizado.
     */
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "usuarios", key = "#id"),
        @CacheEvict(value = "usuariosPorEmail", allEntries = true) // Simplificado
    })
    public UsuarioDTO atualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        log.info("Tentativa de atualizar usuário com ID: {}", id);
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado para atualização com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });

        // Validação de email duplicado na atualização
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

    /**
     * Deleta um usuário pelo ID.
     * @param id ID do usuário.
     */
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "usuarios", key = "#id"),
        @CacheEvict(value = "usuariosPorEmail", allEntries = true) // Simplificado
    })
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
            // Mensagem atualizada para o novo contexto (não mais veículos)
            throw new BusinessException("Não é possível deletar o usuário pois ele possui dados relacionados (ex: competências associadas).");
        }
    }
}