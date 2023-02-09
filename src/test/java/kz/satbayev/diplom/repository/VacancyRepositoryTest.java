package kz.satbayev.diplom.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kz.satbayev.diplom.builder.VacancyBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class VacancyRepositoryTest extends RepositoryTest {

  @Autowired
  private VacancyRepository vacancyRepository;

  @Test
  void test() {

    final var aVacancy = db.save(aVacancy());

    final var vacancy = vacancyRepository.findById(aVacancy.getId());

    assertThat(vacancy).isNotEmpty();
  }

}
