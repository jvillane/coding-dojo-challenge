package cl.jvillane.codingdojo.challenge.security;

import cl.jvillane.codingdojo.challenge.model.User;
import cl.jvillane.codingdojo.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = repository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new CustomUserPrincipal(user);
  }
}
