package cl.jvillane.codingdojo.challenge.projection;

public interface BorrowerByLenderProjection {
  Long getId();

  String getFirstName();

  String getLastName();

  String getReason();

  String getDescription();

  Long getMoney();

  Long getLent();

  Long getRaised();
}
