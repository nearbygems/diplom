package kz.satbayev.diplom.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kz.satbayev.diplom.builder.CompanyBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class CompanyRepositoryTest extends RepositoryTest {

  @Autowired
  private CompanyRepository repository;

  @Test
  void test() {

    final var aCompany = db.save(aCompany());

    final var company = repository.findById(aCompany.getId());

    assertThat(company).isNotEmpty();
  }

}
