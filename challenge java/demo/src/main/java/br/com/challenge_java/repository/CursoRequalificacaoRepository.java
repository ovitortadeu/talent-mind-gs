package br.com.challenge_java.repository;

import br.com.challenge_java.model.CursoRequalificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRequalificacaoRepository extends JpaRepository<CursoRequalificacao, Long> {
}