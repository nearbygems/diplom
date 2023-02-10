package kz.satbayev.diplom.converter.impl;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.model.db.Company;
import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import org.jsoup.nodes.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultConverter implements Converter {

  @Override
  public Vacancy convert(ParsedVacancy parsedVacancy) {

    final var company = new Company();
    company.setId(parsedVacancy.getCompanyId());
    company.setName(parsedVacancy.getCompanyName());
    company.setCity(parsedVacancy.getCity());

    final var vacancy = new Vacancy();
    vacancy.setId(parsedVacancy.getId());
    vacancy.setTitle(parsedVacancy.getTitle());
    extractLong(parsedVacancy.getSalary()).ifPresent(vacancy :: setSalary);
    vacancy.setDescription(parsedVacancy.getDescription());
    vacancy.setRequirements(parsedVacancy.getRequirements());
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
    vacancyId   (element).ifPresent(vacancy :: setId          );
    title       (element).ifPresent(vacancy :: setTitle       );
    salary      (element).ifPresent(vacancy :: setSalary      );
    companyId   (element).ifPresent(vacancy :: setCompanyId   );
    companyName (element).ifPresent(vacancy :: setCompanyName );
    city        (element).ifPresent(vacancy :: setCity        );
    // @formatter:on

    return vacancy;
  }

  private Optional<Long> vacancyId(Element element) {
    return Optional.of(element.getElementsByClass("serp-item__title"))
                   .map(elem -> elem.attr("href"))
                   .flatMap(this :: extractLong);
  }

  private Optional<String> title(Element element) {
    return Optional.of(element.getElementsByClass("serp-item__title"))
                   .flatMap(elem -> elem.stream()
                                        .map(Node :: childNodes)
                                        .filter(nodes -> !nodes.isEmpty())
                                        .flatMap(List :: stream)
                                        .filter(TextNode.class :: isInstance)
                                        .map(TextNode.class :: cast)
                                        .filter(node -> !node.isBlank())
                                        .map(TextNode :: text)
                                        .findAny());
  }

  private Optional<String> salary(Element element) {
    return Optional.of(element.getElementsByClass("bloko-header-section-3"))
                   .flatMap(elem -> elem.stream()
                                        .filter(e -> e.tagName().equals("span"))
                                        .findAny()
                                        .map(Node :: childNodes)
                                        .filter(nodes -> !nodes.isEmpty())
                                        .flatMap(nodes -> nodes.stream().findAny())
                                        .filter(TextNode.class :: isInstance)
                                        .map(TextNode.class :: cast)
                                        .filter(node -> !node.isBlank())
                                        .map(TextNode :: text));
  }

  private Optional<Long> companyId(Element element) {
    return Optional.of(element.getElementsByClass("bloko-link bloko-link_kind-tertiary"))
                   .map(elem -> elem.attr("href"))
                   .flatMap(this :: extractLong);
  }

  private Optional<String> companyName(Element element) {
    return Optional.of(element.getElementsByClass("bloko-link bloko-link_kind-tertiary"))
                   .flatMap(elem -> elem.stream()
                                        .findAny()
                                        .map(Node :: childNodes)
                                        .filter(nodes -> !nodes.isEmpty())
                                        .flatMap(nodes -> nodes.stream().findAny())
                                        .filter(TextNode.class :: isInstance)
                                        .map(TextNode.class :: cast)
                                        .filter(node -> !node.isBlank())
                                        .map(TextNode :: text));
  }

  private Optional<String> city(Element element) {
    return Optional.of(element.getElementsByClass("bloko-text"))
                   .flatMap(elem -> elem.stream()
                                        .filter(e -> e.attr("data-qa").equals("vacancy-serp__vacancy-address"))
                                        .findAny()
                                        .map(Node :: childNodes)
                                        .filter(nodes -> !nodes.isEmpty())
                                        .flatMap(nodes -> nodes.stream().findAny())
                                        .filter(TextNode.class :: isInstance)
                                        .map(TextNode.class :: cast)
                                        .filter(node -> !node.isBlank())
                                        .map(TextNode :: text));
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

}
