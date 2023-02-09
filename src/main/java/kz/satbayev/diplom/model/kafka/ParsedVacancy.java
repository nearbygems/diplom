package kz.satbayev.diplom.model.kafka;

import lombok.Data;

@Data
public class ParsedVacancy {

  private Long   id;
  private String title;
  private String salary;
  private String description;
  private String requirements;

  private String companyId;
  private String companyName;
  private String city;

}
