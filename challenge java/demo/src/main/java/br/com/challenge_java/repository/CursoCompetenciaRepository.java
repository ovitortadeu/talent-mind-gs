package br.com.challenge_java.repository;

import br.com.challenge_java.model.CursoCompetencia;
import br.com.challenge_java.model.CursoCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade de associação CursoCompetencia,
 * que liga Cursos a Competências.
 */
@Repository
public interface CursoCompetenciaRepository extends JpaRepository<CursoCompetencia, CursoCompetenciaId> {
}