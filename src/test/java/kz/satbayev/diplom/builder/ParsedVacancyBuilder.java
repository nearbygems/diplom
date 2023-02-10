package kz.satbayev.diplom.builder;

import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "aVacancy")
public class ParsedVacancyBuilder implements TestBuilder<ParsedVacancy> {

  private Long   id           = 0L;
  private String title        = "Java developer";
  private Long   salary       = 1_000_000L;
  private String description  = "Some description";
  private String requirements = "Some requirements";

  private Long   companyId   = 0L;
  private String companyName = "Satbayev University";
  private String city        = "Astana";

  @Override
  public ParsedVacancy build() {

    final var vacancy = new ParsedVacancy();

    vacancy.setId(id);
    vacancy.setTitle(title);
    vacancy.setSalary(salary);
    vacancy.setResponsibility(description);
    vacancy.setRequirement(requirements);

    vacancy.setCompanyId(companyId);
    vacancy.setCompanyName(companyName);
    vacancy.setCity(city);

    return vacancy;
  }

}
