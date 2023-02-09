package kz.satbayev.diplom.converter;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;

import java.util.List;

public interface Converter {

  Vacancy convert(ParsedVacancy parsedVacancy);

  List<ParsedVacancy> convert(String html);

}
