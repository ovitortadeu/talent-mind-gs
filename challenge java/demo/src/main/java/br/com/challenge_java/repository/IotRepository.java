package br.com.challenge_java.repository;

import br.com.challenge_java.model.Iot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IotRepository extends JpaRepository<Iot, Long> {
}