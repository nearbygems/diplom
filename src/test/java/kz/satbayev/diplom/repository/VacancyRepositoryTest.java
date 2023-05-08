package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.web.SalaryRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

import static kz.satbayev.diplom.builder.VacancyBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class VacancyRepositoryTest extends RepositoryTest {

  @Autowired
  private VacancyRepository repository;

  @Test
  void test() {

    final var aVacancy = db.save(aVacancy());

    final var vacancy = repository.findById(aVacancy.getId());

    assertThat(vacancy).isNotEmpty();
  }

  @Test
  void findAllByTitleContainsIgnoreCase() {

    final var title = "title";

    final var aVacancy = db.save(aVacancy().withTitle("some " + title + " in the vacancy"));
    db.save(aVacancy());

    final var vacancies = repository.findAllByTitleContainsIgnoreCase(title, PageRequest.of(0, 10));

    assertThat(vacancies).isNotEmpty();
    assertThat(vacancies).hasSize(1);

    final var ids = vacancies.stream().map(Vacancy::getId).collect(Collectors.toSet());

    assertThat(ids).contains(aVacancy.getId());
  }

  @Test
  void findAllBySalaryBetween() {

    final var range = SalaryRange.from(450_000L, 550_000L);

    final var aVacancy = db.save(aVacancy().withSalary(500_000L));
    db.save(aVacancy());

    final var vacancies = repository.findAllBySalaryBetween(range.getFrom(), range.getTo(),
            PageRequest.of(0, 10));

    assertThat(vacancies).isNotEmpty();
    assertThat(vacancies).hasSize(1);

    final var ids = vacancies.stream().map(Vacancy::getId).collect(Collectors.toSet());

    assertThat(ids).contains(aVacancy.getId());
  }

  @Test
  void findAllByResponsibilityContainsIgnoreCase() {

    final var responsibility = "responsibility";

    final var aVacancy = db.save(aVacancy().withResponsibility("some " + responsibility + " in the vacancy"));
    db.save(aVacancy());

    final var vacancies = repository.findAllByResponsibilityContainsIgnoreCase(responsibility, PageRequest.of(0, 10));

    assertThat(vacancies).isNotEmpty();
    assertThat(vacancies).hasSize(1);

    final var ids = vacancies.stream().map(Vacancy::getId).collect(Collectors.toSet());

    assertThat(ids).contains(aVacancy.getId());
  }

  @Test
  void findAllByRequirementContainsIgnoreCase() {

    final var requirement = "needs";

    final var aVacancy = db.save(aVacancy().withRequirement("some " + requirement + " in the vacancy"));
    db.save(aVacancy());

    final var vacancies = repository.findAllByRequirementContainsIgnoreCase(requirement, PageRequest.of(0, 10));

    assertThat(vacancies).isNotEmpty();
    assertThat(vacancies).hasSize(1);

    final var ids = vacancies.stream().map(Vacancy::getId).collect(Collectors.toSet());

    assertThat(ids).contains(aVacancy.getId());
  }

}
