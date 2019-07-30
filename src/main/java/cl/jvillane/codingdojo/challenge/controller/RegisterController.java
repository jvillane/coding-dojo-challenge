package cl.jvillane.codingdojo.challenge.controller;

import cl.jvillane.codingdojo.challenge.model.Borrower;
import cl.jvillane.codingdojo.challenge.model.Lender;
import cl.jvillane.codingdojo.challenge.repository.BorrowerRepository;
import cl.jvillane.codingdojo.challenge.repository.LenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/register")
public class RegisterController {

  @Autowired
  private BorrowerRepository borrowerRepository;
  @Autowired
  private LenderRepository lenderRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/borrower")
  public Borrower borrower(@RequestBody Borrower borrower) {
    try {
      if (borrower.getId() == null) {
        borrower.setPassword(passwordEncoder.encode(borrower.getPassword()));
        borrower.setType("BORROWER");
        return borrowerRepository.save(borrower);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not allowed");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request", e);
    }
  }

  @PostMapping("/lender")
  public Lender lender(@RequestBody Lender lender) {
    try {
      if (lender.getId() == null) {
        lender.setPassword(passwordEncoder.encode(lender.getPassword()));
        lender.setType("LENDER");
        return lenderRepository.save(lender);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not allowed");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request", e);
    }
  }
}
