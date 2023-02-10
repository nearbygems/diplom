package kz.satbayev.diplom.converter.impl;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.model.db.Company;
import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultConverter implements Converter {

  private static final String TITLE          = "serp-item__title";
  private static final String SALARY         = "vacancy-serp__vacancy-compensation";
  private static final String RESPONSIBILITY = "vacancy-serp__vacancy_snippet_responsibility";
  private static final String REQUIREMENT    = "vacancy-serp__vacancy_snippet_requirement";
  private static final String COMPANY        = "vacancy-serp__vacancy-employer";
  private static final String CITY           = "vacancy-serp__vacancy-address";

  @Override
  public Vacancy convert(ParsedVacancy parsedVacancy) {

    final var company = new Company();
    company.setId(parsedVacancy.getCompanyId());
    company.setName(parsedVacancy.getCompanyName());
    company.setCity(parsedVacancy.getCity());

    final var vacancy = new Vacancy();
    vacancy.setId(parsedVacancy.getId());
    vacancy.setTitle(parsedVacancy.getTitle());
    vacancy.setSalary(parsedVacancy.getSalary());
    vacancy.setResponsibility(parsedVacancy.getResponsibility());
    vacancy.setRequirement(parsedVacancy.getRequirement());
    vacancy.setCompany(company);

    return vacancy;
  }

  @Override
  public List<ParsedVacancy> convert(Document document) {

    return document.getElementsByClass("serp-item").stream()
                   .map(this :: vacancy)
                   .collect(Collectors.toList());
  }

  private ParsedVacancy vacancy(Element element) {

    final var vacancy = new ParsedVacancy();

    // @formatter:off
    vacancyId       ( element ) . ifPresent ( vacancy :: setId             );
    title           ( element ) . ifPresent ( vacancy :: setTitle          );
    salary          ( element ) . ifPresent ( vacancy :: setSalary         );
    responsibility  ( element ) . ifPresent ( vacancy :: setResponsibility );
    requirement     ( element ) . ifPresent ( vacancy :: setRequirement    );
    companyId       ( element ) . ifPresent ( vacancy :: setCompanyId      );
    companyName     ( element ) . ifPresent ( vacancy :: setCompanyName    );
    city            ( element ) . ifPresent ( vacancy :: setCity           );
    // @formatter:on

    return vacancy;
  }

  private Optional<Long> vacancyId(Element element) {
    return Optional.of(element.getElementsByClass("serp-item__title"))
                   .map(elem -> elem.attr("href"))
                   .flatMap(this :: extractLong);
  }

  private Optional<String> title(Element element) {
    return Optional.of(element.getElementsByClass(TITLE))
                   .flatMap(elem -> elem.stream()
                                        .filter(e -> e.attr("data-qa").equals(TITLE))
                                        .map(Node :: childNodes)
                                        .filter(nodes -> !nodes.isEmpty())
                                        .flatMap(List :: stream)
                                        .filter(TextNode.class :: isInstance)
                                        .map(TextNode.class :: cast)
                                        .filter(node -> !node.isBlank())
                                        .map(TextNode :: text)
                                        .map(String :: trim).findAny());
  }

  private Optional<Long> salary(Element element) {
    return Optional.of(element.getElementsByClass("bloko-header-section-3"))
                   .flatMap(elem -> find(elem, SALARY)
                       .flatMap(this :: extractLong));
  }

  private Optional<String> responsibility(Element element) {
    return findText(element, RESPONSIBILITY);
  }

  private Optional<String> requirement(Element element) {
    return findText(element, REQUIREMENT);
  }

  private Optional<Long> companyId(Element element) {
    return Optional.of(element.getElementsByClass("bloko-link bloko-link_kind-tertiary"))
                   .filter(e -> e.attr("data-qa").equals(COMPANY))
                   .map(elem -> elem.attr("href"))
                   .flatMap(this :: extractLong);
  }

  private Optional<String> companyName(Element element) {
    return Optional.of(element.getElementsByClass("bloko-link bloko-link_kind-tertiary"))
                   .flatMap(elem -> find(elem, COMPANY));
  }

  private Optional<String> city(Element element) {
    return findText(element, CITY);
  }

  private Optional<Long> extractLong(String str) {
    return Optional.ofNullable(str)
                   .map(s -> s.replaceAll("\\D+", ""))
                   .map(s -> {
                          try {
                            return Long.parseLong(s);
                          } catch (Exception e) {
                            return null;
                          }
                        }
                       );
  }

  private Optional<String> findText(Element element, String data) {
    return Optional.of(element.getElementsByClass("bloko-text"))
                   .flatMap(elem -> find(elem, data));
  }

  private Optional<String> find(Elements elements, String data) {
    return elements.stream()
                   .filter(e -> e.attr("data-qa").equals(data))
                   .findAny()
                   .map(Node :: childNodes)
                   .filter(nodes -> !nodes.isEmpty())
                   .flatMap(nodes -> nodes.stream().findAny())
                   .filter(TextNode.class :: isInstance)
                   .map(TextNode.class :: cast)
                   .filter(node -> !node.isBlank())
                   .map(TextNode :: text)
                   .map(String :: trim);
  }

}
