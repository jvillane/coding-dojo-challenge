package cl.jvillane.codingdojo.challenge.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
  Long id;
  String username;
  String role;
}
