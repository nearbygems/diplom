package kz.satbayev.diplom.model.kafka;

import lombok.Data;

@Data
public class ParsedVacancy {

  private Long   id;
  private String title;
  private Long   salary;
  private String responsibility;
  private String requirement;

  private Long   companyId;
  private String companyName;
  private String city;

}
