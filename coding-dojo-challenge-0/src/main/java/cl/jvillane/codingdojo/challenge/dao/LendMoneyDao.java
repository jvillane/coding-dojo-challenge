package cl.jvillane.codingdojo.challenge.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LendMoneyDao {
  Long borrowerId;
  Long amount;
}
