package br.com.challenge_java.repository;

import br.com.challenge_java.model.VagaCompetencia;
import br.com.challenge_java.model.VagaCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade de associação VagaCompetencia,
 * que liga Vagas a Competências.
 */
@Repository
public interface VagaCompetenciaRepository extends JpaRepository<VagaCompetencia, VagaCompetenciaId> {
}