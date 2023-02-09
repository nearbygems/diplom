package kz.satbayev.diplom.builder;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "aVacancy")
public class VacancyBuilder implements TestBuilder<Vacancy> {

  private Long   id;
  private String title = "";

  @Override
  public Vacancy build() {
    final var vacancy = new Vacancy();
    vacancy.setTitle(title);
    return vacancy;
  }

}
