package kz.satbayev.diplom.model.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Company {

  @Id
  private Long   id;
  private String name;
  private String city;

}
