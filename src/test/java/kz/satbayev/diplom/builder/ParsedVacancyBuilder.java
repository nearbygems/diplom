package kz.satbayev.diplom.builder;

import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "aVacancy")
public class ParsedVacancyBuilder implements TestBuilder<ParsedVacancy> {

  private Long   id           = 0L;
  private String title        = "Java developer";
  private String salary       = "ДО 1 000 000 тенге";
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
    vacancy.setDescription(description);
    vacancy.setRequirements(requirements);

    vacancy.setCompanyId(companyId);
    vacancy.setCompanyName(companyName);
    vacancy.setCity(city);

    return vacancy;
  }

}
