package cl.jvillane.codingdojo.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("BORROWER")
public class Borrower extends User {
  String reason;
  String description;
  long money;
}
