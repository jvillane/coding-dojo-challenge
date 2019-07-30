package cl.jvillane.codingdojo.challenge.repository;

import cl.jvillane.codingdojo.challenge.model.Lender;
import cl.jvillane.codingdojo.challenge.projection.LenderByBorrowedProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LenderRepository extends CrudRepository<Lender, Long> {

  @Query(value = "select l.first_name as firstName, l.last_name as lastName, l.email as email, IFNULL(SUM(i.ammount), 0) as lent " +
      "from user l join interaction i on i.lender_id = l.id " +
      "where i.borrower_id = :borrowerId and l.type = 'LENDER' " +
      "group by firstName, lastName, email", nativeQuery = true)
  List<LenderByBorrowedProjection> findByBorrowerId(Long borrowerId);
}
