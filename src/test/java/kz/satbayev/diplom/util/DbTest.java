package kz.satbayev.diplom.util;

import kz.satbayev.diplom.repository.VacancyRepositoryTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DataJpaTest
@Import({TestDbFacade.Config.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {VacancyRepositoryTest.Initializer.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTest {}
