package kz.satbayev.diplom.kafka;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import kz.satbayev.diplom.service.VacancyService;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Consumer {

  private final Converter      converter;
  private final VacancyService service;

  @KafkaListener(topics = "vacancy", batch = "true")
  public void consume(List<ParsedVacancy> parsedVacancies) {

    final var vacancies = StreamEx.of(parsedVacancies)
                                  .map(converter :: convert)
                                  .toList();

    service.save(vacancies);
  }

}
