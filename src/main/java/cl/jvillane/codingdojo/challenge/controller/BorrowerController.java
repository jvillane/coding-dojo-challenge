package cl.jvillane.codingdojo.challenge.controller;

import cl.jvillane.codingdojo.challenge.model.Borrower;
import cl.jvillane.codingdojo.challenge.projection.LenderByBorrowedProjection;
import cl.jvillane.codingdojo.challenge.projection.PendingBorrowerProjection;
import cl.jvillane.codingdojo.challenge.repository.BorrowerRepository;
import cl.jvillane.codingdojo.challenge.repository.LenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {

  @Autowired
  private BorrowerRepository repository;

  @Autowired
  private LenderRepository lenderRepository;

  @GetMapping
  public Iterable<Borrower> getList() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Borrower getById(Principal principal, @PathVariable Long id) {
    Optional<Borrower> optional = repository.findById(id);
    if (optional.isPresent()) {
      Borrower borrower = optional.get();
      if (borrower.getEmail().equals(principal.getName())) {
        return borrower;
      } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id");
    }
  }

  @GetMapping("/pending")
  public List<PendingBorrowerProjection> pending() {
    return repository.pendingBorrowers();
  }

  @GetMapping("/{id}/lenders")
  public List<LenderByBorrowedProjection> lenders(Principal principal, @PathVariable Long id) {
    //TODO verificar que el usuario logeado corresponda al id ingresado
    return lenderRepository.findByBorrowerId(id);
  }
}
