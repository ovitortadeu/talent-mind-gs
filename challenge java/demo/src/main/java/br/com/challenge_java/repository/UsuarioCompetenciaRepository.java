package br.com.challenge_java.repository;

import br.com.challenge_java.model.UsuarioCompetencia;
import br.com.challenge_java.model.UsuarioCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioCompetenciaRepository extends JpaRepository<UsuarioCompetencia, UsuarioCompetenciaId> {
}