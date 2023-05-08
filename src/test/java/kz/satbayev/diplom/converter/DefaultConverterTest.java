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
    assertThat(vacancy.getResponsibility()).isEqualTo(parsed.getResponsibility());
    assertThat(vacancy.getRequirement()).isEqualTo(parsed.getRequirement());

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
      final var vacancies = converter.convert(document);
      //

      final var vacancy = vacancies.stream().findAny();

      assertThat(vacancy).isNotEmpty();

      vacancy.ifPresent(v -> {
        assertThat(v.getId()).isEqualTo(76587108);
        assertThat(v.getTitle()).isEqualTo("IT-инженер/системный администратор");
        assertThat(v.getSalary()).isEqualTo(400000);
        assertThat(v.getResponsibility()).contains(
            "Сопровождение работы РЦ (склада) по IT направлению.");
        assertThat(v.getRequirement()).contains(
            "Опыт работы в сфере IT не менее 1 года.");
        assertThat(v.getCompanyId()).isEqualTo(4641910);
        assertThat(v.getCompanyName()).isEqualTo("ТОО Бэст Прайс Казахстан");
        assertThat(v.getCity()).contains("Астана");

      });

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
