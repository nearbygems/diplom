package kz.satbayev.diplom.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vacancy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long   id;
  private String title;
  private Long   salary;
  private String responsibility;
  private String requirement;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;

}
