package cl.jvillane.codingdojo.challenge.projection;

public interface PendingBorrowerProjection {
  Long getId();

  String getFirstName();

  String getLastName();

  String getReason();

  String getDescription();

  Long getMoney();

  Long getSum();
}
