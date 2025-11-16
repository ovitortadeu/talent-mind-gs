package br.com.challenge_java.repository;

import br.com.challenge_java.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByPlacaNova(String placaNova);

    List<Veiculo> findByUsuarioId(Long usuarioId);
    
    List<Veiculo> findByUsuarioUsername(String username);
}