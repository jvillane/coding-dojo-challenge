package cl.jvillane.codingdojo.challenge.repository;

import cl.jvillane.codingdojo.challenge.model.Interaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends CrudRepository<Interaction, Long> {
}
