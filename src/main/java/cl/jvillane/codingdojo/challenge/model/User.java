package cl.jvillane.codingdojo.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(unique = true)
  String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String password;
  String firstName;
  String lastName;
  @Column(name = "type", insertable = false, updatable = false)
  String type;
}
