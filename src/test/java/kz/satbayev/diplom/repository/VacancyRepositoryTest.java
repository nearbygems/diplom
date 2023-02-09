package kz.satbayev.diplom.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kz.satbayev.diplom.entity.VacancyBuilder.aVacancy;
import static org.assertj.core.api.Assertions.assertThat;

public class VacancyRepositoryTest extends RepositoryTest {

  @Autowired
  private VacancyRepository repository;

  @Test
  void test() {

    db.saveAll(
        aVacancy()
            .withTitle("title1"),
        aVacancy()
            .withTitle("title2")
              );

    final var title1 = repository.findByTitle("title1");
    final var title2 = repository.findByTitle("title2");

    assertThat(title1).isNotEmpty();
    assertThat(title2).isNotEmpty();
  }


}
