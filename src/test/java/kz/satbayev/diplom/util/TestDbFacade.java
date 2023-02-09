package kz.satbayev.diplom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;

public class TestDbFacade {

  @Autowired
  private TestEntityManager   testEntityManager;
  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private JdbcTemplate        jdbcTemplate;

  public void cleanDatabase() {
    transactionTemplate.execute(status -> {
      JdbcTestUtils.deleteFromTables(jdbcTemplate, "vacancy");
      return null;
    });
  }

  public <T> T find(Object id, Class<T> entityClass) {
    return transactionTemplate.execute(status -> testEntityManager.find(entityClass, id));
  }

  public void saveAll(TestBuilder<?>... builders) {
    transactionTemplate.execute(status -> {
      Arrays.stream(builders).forEach(this :: save);
      return null;
    });
  }

  public <T> T save(TestBuilder<T> builder) {
    return transactionTemplate.execute(status -> testEntityManager.persistAndFlush(builder.build()));
  }

  public <T> TestBuilder<T> persistedOnce(TestBuilder<T> builder) {

    return new TestBuilder<>() {

      private T entity;

      @Override
      public T build() {
        if (entity == null) {
          entity = save(builder);
        }
        return entity;
      }
    };

  }

  @TestConfiguration
  public static class Config {

    @Bean
    public TestDbFacade testDBFacade() {
      return new TestDbFacade();
    }

  }

}
