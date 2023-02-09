package kz.satbayev.diplom.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kz.satbayev.diplom.builder.VacancyBuilder.aVacancy;
import static org.assertj.core.api.Assertions.assertThat;

public class VacancyRepositoryTest extends RepositoryTest {

  @Autowired
  private VacancyRepository repository;

  @Test
  void test() {

    final var id = db.save(
                         aVacancy()
                             .withTitle("title")
                          )
                     .getId();

    final var vacancy = repository.findById(id);

    assertThat(vacancy).isNotEmpty();
  }

}
