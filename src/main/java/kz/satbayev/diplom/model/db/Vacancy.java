package kz.satbayev.diplom.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vacancy {

  @Id
  private Long   id;
  private String title;
  private Long   salary;
  private String description;
  private String requirements;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;

}
