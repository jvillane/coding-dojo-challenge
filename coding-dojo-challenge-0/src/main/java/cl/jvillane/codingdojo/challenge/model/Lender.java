package cl.jvillane.codingdojo.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("LENDER")
public class Lender extends User {
  long money;
}
