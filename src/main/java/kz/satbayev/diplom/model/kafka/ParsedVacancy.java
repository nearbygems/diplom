package kz.satbayev.diplom.model.kafka;

import lombok.Data;

@Data
public class ParsedVacancy {

  private String hhId;
  private String title;
  private String salary;
  private String companyId;
  private String companyName;


}
