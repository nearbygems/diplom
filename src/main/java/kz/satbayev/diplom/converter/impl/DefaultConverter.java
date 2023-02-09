package kz.satbayev.diplom.converter.impl;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import org.springframework.stereotype.Component;

@Component
public class DefaultConverter implements Converter {

  @Override
  public Vacancy convert(ParsedVacancy parsedVacancy) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
