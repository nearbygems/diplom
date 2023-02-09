package kz.satbayev.diplom.service;

import kz.satbayev.diplom.model.db.Vacancy;

import java.util.List;

public interface VacancyService {

  void save(List<Vacancy> vacancies);

}
