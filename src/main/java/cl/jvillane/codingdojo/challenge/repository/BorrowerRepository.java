package cl.jvillane.codingdojo.challenge.repository;

import cl.jvillane.codingdojo.challenge.model.Borrower;
import cl.jvillane.codingdojo.challenge.model.Lender;
import cl.jvillane.codingdojo.challenge.projection.BorrowerByLenderProjection;
import cl.jvillane.codingdojo.challenge.projection.PendingBorrowerProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends CrudRepository<Borrower, Long> {

  @Query("select b from Interaction i inner join i.lender l inner join i.borrower b where l.id = :lenderId")
  List<Borrower> findByLenderId(Long lenderId);

  @Query(value = "select u.id as id, u.first_name as firstName, u.last_name as lastName, u.reason as reason, u.description as description, u.money as money, IFNULL(SUM(i.ammount), 0) as sum " +
      "from user u left join interaction i on i.borrower_id = u.id " +
      "where u.type = 'BORROWER' " +
      "group by id, first_name, last_name, reason, description, money " +
      "having u.money > sum", nativeQuery = true)
  List<PendingBorrowerProjection> pendingBorrowers();

  @Query(value = "select lented.id as id, lented.first_name as firstName, lented.last_name as lastName, lented.reason as reason, lented.description as description, lented.money as money, lented.total as lent, raised.total as raised " +
      "from (select b1.id as id, IFNULL(SUM(i1.ammount), 0) as total from user b1 left join interaction i1 on i1.borrower_id = b1.id " +
      "where b1.type = 'BORROWER' group by id) as raised join " +
      "    (select b2.id as id, b2.first_name as first_name, b2.last_name as last_name, b2.reason as reason, b2.description as description, b2.money as money, IFNULL(SUM(i2.ammount), 0) as total " +
      "    from user b2 left join interaction i2 on i2.borrower_id = b2.id " +
      "where b2.type = 'BORROWER' and i2.lender_id = :lenderId group by id, first_name, last_name, reason, description, money) as lented on raised.id = lented.id", nativeQuery = true)
  List<BorrowerByLenderProjection> borrowersByLenderId(Long lenderId);
}
