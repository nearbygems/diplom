package kz.satbayev.diplom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;

public class TestDbFacade {

  @Autowired
  private TestEntityManager   testEntityManager;
  @Autowired
  private JdbcTemplate        jdbcTemplate;

  public void cleanDatabase() {
      JdbcTestUtils.deleteFromTables(jdbcTemplate, "vacancy");
      JdbcTestUtils.deleteFromTables(jdbcTemplate, "company");
  }

  @SuppressWarnings("unused")
  public <T> T find(Object id, Class<T> entityClass) {
    return  testEntityManager.find(entityClass, id);
  }

  @SuppressWarnings("unused")
  public void saveAll(TestBuilder<?>... builders) {
      Arrays.stream(builders).forEach(this :: save);
  }

  @SuppressWarnings("unused")
  public <T> T save(TestBuilder<T> builder) {
    return testEntityManager.persistAndFlush(builder.build());
  }

  @TestConfiguration
  public static class Config {

    @Bean
    public TestDbFacade testDBFacade() {
      return new TestDbFacade();
    }

  }

}
