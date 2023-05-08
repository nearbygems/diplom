package kz.satbayev.diplom.service;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.web.SalaryRange;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService {

  void save(List<Vacancy> vacancies);

  List<Vacancy> loadByTitle(String title, Pageable page);

  List<Vacancy> loadBySalary(SalaryRange range, Pageable page);

  List<Vacancy> loadByResponsibility(String responsibility, Pageable page);

  List<Vacancy> loadByRequirement(String requirement, Pageable page);

}
