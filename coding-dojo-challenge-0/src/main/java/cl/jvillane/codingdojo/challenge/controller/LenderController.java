package cl.jvillane.codingdojo.challenge.controller;

import cl.jvillane.codingdojo.challenge.dao.LendMoneyDao;
import cl.jvillane.codingdojo.challenge.model.Borrower;
import cl.jvillane.codingdojo.challenge.model.Interaction;
import cl.jvillane.codingdojo.challenge.model.Lender;
import cl.jvillane.codingdojo.challenge.projection.BorrowerByLenderProjection;
import cl.jvillane.codingdojo.challenge.repository.BorrowerRepository;
import cl.jvillane.codingdojo.challenge.repository.InteractionRepository;
import cl.jvillane.codingdojo.challenge.repository.LenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lender")
public class LenderController {

  @Autowired
  private BorrowerRepository borrowerRepository;

  @Autowired
  private LenderRepository repository;

  @Autowired
  private InteractionRepository interactionRepository;

  @GetMapping
  public Iterable<Borrower> getList() {
    return borrowerRepository.findAll();
  }

  @GetMapping("/{id}")
  public Lender getById(Principal principal, @PathVariable Long id) {
    Optional<Lender> optional = repository.findById(id);
    if (optional.isPresent()) {
      Lender lender = optional.get();
      if (lender.getEmail().equals(principal.getName())) {
        return lender;
      } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id");
    }
  }

  @GetMapping("/{id}/borrowers")
  public List<BorrowerByLenderProjection> borrowers(Principal principal, @PathVariable Long id) {
    //TODO verificar que el usuario logeado corresponda al id ingresado
    return borrowerRepository.borrowersByLenderId(id);
  }

  @PostMapping("/{id}/lend")
  public Interaction lend(Principal principal, @PathVariable Long id, @RequestBody LendMoneyDao body) {
    Optional<Lender> optionalLender = repository.findById(id);
    Lender lender = null;
    Borrower borrower = null;
    if (optionalLender.isPresent()) {
      lender = optionalLender.get();
      if (!lender.getEmail().equals(principal.getName())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid lender id");
    }

    Optional<Borrower> optionalBorrower = borrowerRepository.findById(body.getBorrowerId());
    if (optionalBorrower.isPresent()) {
      borrower = optionalBorrower.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid borrower id");
    }

    Interaction interaction = Interaction.builder()
        .lender(lender)
        .borrower(borrower)
        .ammount(body.getAmount())
        .build();
    return interactionRepository.save(interaction);
  }
}
