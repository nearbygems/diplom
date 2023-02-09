package kz.satbayev.diplom.entity;

import kz.satbayev.diplom.model.Vacancy;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aVacancy")
public class VacancyBuilder implements TestBuilder<Vacancy> {

  private String title = "";

  @Override
  public Vacancy build() {
    final var vacancy = new Vacancy();
    vacancy.setTitle(title);
    return vacancy;
  }

}
