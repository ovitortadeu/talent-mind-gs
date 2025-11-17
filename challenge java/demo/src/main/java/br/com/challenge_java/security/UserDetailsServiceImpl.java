package br.com.challenge_java.security;

import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carrega os detalhes do usuário pelo seu email (usado como username no Spring Security).
     *
     * @param email O email do usuário (enviado como 'username' pelo AuthenticationManager).
     * @return UserDetails contendo os dados do usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        // Retorna o UserDetails com as autoridades (roles) já definidas na entidade Usuario
        return usuario;
    }
}