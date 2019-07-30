package cl.jvillane.codingdojo.challenge.repository;

import cl.jvillane.codingdojo.challenge.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(String email);
}
