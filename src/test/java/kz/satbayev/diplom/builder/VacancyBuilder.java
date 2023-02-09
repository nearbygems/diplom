package kz.satbayev.diplom.builder;

import kz.satbayev.diplom.model.db.Company;
import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import static kz.satbayev.diplom.builder.CompanyBuilder.*;


@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "aVacancy")
public class VacancyBuilder implements TestBuilder<Vacancy> {

  private Long    id           = 0L;
  private String  title        = "Java developer";
  private Long    salary       = 1_000_000L;
  private String  description  = "Some description";
  private String  requirements = "Some requirements";

  private Company company      = aCompany().build();

  @Override
  public Vacancy build() {

    final var vacancy = new Vacancy();

    vacancy.setId(id);
    vacancy.setTitle(title);
    vacancy.setSalary(salary);
    vacancy.setDescription(description);
    vacancy.setRequirements(requirements);

    vacancy.setCompany(company);

    return vacancy;
  }

}
