package kz.satbayev.diplom.converter;

import kz.satbayev.diplom.converter.impl.DefaultConverter;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static kz.satbayev.diplom.builder.ParsedVacancyBuilder.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {DefaultConverter.class})
public class DefaultConverterTest {

  final String charset = "UTF-8";

  @Autowired
  private Converter converter;

  @Test
  void convertParsedVacancy() {

    final var parsed = aVacancy().build();

    //
    final var vacancy = converter.convert(parsed);
    //

    assertThat(vacancy.getId()).isEqualTo(parsed.getId());
    assertThat(vacancy.getTitle()).isEqualTo(parsed.getTitle());
    assertThat(vacancy.getSalary()).isEqualTo(1_000_000L);
    assertThat(vacancy.getDescription()).isEqualTo(parsed.getDescription());
    assertThat(vacancy.getRequirements()).isEqualTo(parsed.getRequirements());

    final var company = vacancy.getCompany();

    assertThat(company.getId()).isEqualTo(parsed.getCompanyId());
    assertThat(company.getName()).isEqualTo(parsed.getCompanyName());
    assertThat(company.getCity()).isEqualTo(parsed.getCity());
  }

  @Test
  void convertHtml() {

    try (final var html = new ClassPathResource("test.html").getInputStream()) {

      final var document = Jsoup.parse(html, charset, "url");

      //
      final var parsedVacancy = converter.convert(document);
      //

      assertThat(parsedVacancy).hasSize(1);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
