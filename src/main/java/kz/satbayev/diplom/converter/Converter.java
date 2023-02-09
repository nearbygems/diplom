package kz.satbayev.diplom.converter;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.kafka.ParsedVacancy;

public interface Converter {

  Vacancy convert(ParsedVacancy parsedVacancy);

}
