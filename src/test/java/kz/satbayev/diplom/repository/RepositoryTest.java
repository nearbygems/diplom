package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.util.DbTest;
import kz.satbayev.diplom.util.TestDbFacade;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@DbTest
@ContextConfiguration(initializers = {RepositoryTest.Initializer.class})
public class RepositoryTest {

  @Autowired
  protected TestDbFacade db;

  @BeforeEach
  public void beforeEach() {
    db.cleanDatabase();
  }

  @ClassRule
  @SuppressWarnings("rawtypes")
  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.0")
      .withDatabaseName("diplom")
      .withUsername("nearbygems")
      .withPassword("123");

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      postgreSQLContainer.start();

      TestPropertyValues.of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                            "spring.jpa.database=" + Database.POSTGRESQL,
                            "spring.jpa.generate-ddl=" + false,
                            "spring.liquibase.change-log=" + "classpath:/db/changelog/db.changelog-master.yaml",
                            "spring.liquibase.enabled=" + true,
                            "spring.liquibase.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.liquibase.user=" + postgreSQLContainer.getUsername(),
                            "spring.liquibase.password=" + postgreSQLContainer.getPassword())
                        .applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}
