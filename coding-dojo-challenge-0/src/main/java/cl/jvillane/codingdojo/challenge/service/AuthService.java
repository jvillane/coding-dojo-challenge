package cl.jvillane.codingdojo.challenge.service;

import cl.jvillane.codingdojo.challenge.dao.UserDao;
import cl.jvillane.codingdojo.challenge.model.User;
import cl.jvillane.codingdojo.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserRepository repository;

  public UserDao getUser(String email) {
    User user = repository.findByEmail(email);
    return UserDao.builder()
        .id(user.getId())
        .username(email)
        .role(user.getType())
        .build();
  }
}
