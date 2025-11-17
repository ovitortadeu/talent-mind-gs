package br.com.challenge_java.model;

import br.com.challenge_java.model.enuns.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.ToString;

@Entity
@Table(name = "T_USUARIO") // Mapeado para a tabela Oracle
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "ID_USUARIO")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME_USUARIO", length = 150, nullable = false)
    private String nomeUsuario; // Adaptado de 'username'

    @Column(name = "EMAIL_USUARIO", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "SENHA_USUARIO", length = 100, nullable = false)
    private String senha;
    
    @CreationTimestamp // Gerenciado pelo Hibernate
    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    private LocalDate dtCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 20, nullable = false) // Coluna mantida para o Spring Security
    private Role role;

    @ToString.Exclude 
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioCompetencia> competencias; // Nova relação

    // Métodos do UserDetails (Adaptados para os novos nomes de campo)

    @Override
    public String getUsername() {
        // O Spring Security usa 'username' por padrão, podemos retornar o email ou o nomeUsuario
        return this.email; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}