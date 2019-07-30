package cl.jvillane.codingdojo.challenge.controller;

import cl.jvillane.codingdojo.challenge.dao.UserDao;
import cl.jvillane.codingdojo.challenge.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService service;

  @GetMapping("/user")
  public UserDao user(Principal principal) {
    try {
      return service.getUser(principal.getName());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid session", e);
    }
  }
}
