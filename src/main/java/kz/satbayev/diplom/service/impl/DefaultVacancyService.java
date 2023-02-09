package kz.satbayev.diplom.service.impl;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultVacancyService implements VacancyService {

  @Override
  public void save(List<Vacancy> vacancies) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
