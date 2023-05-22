package kz.satbayev.diplom.service.impl;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.web.SalaryRange;
import kz.satbayev.diplom.repository.VacancyRepository;
import kz.satbayev.diplom.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

  private final VacancyRepository repository;

  @Override
  public void save(List<Vacancy> vacancies) {
    repository.saveAll(vacancies);
  }

  @Override
  public List<Vacancy> loadByTitle(String title, Pageable page) {
    return Optional.ofNullable(title)
            .map(t -> repository.findAllByTitleContainsIgnoreCase(t, page))
            .orElse(Collections.emptyList());
  }

  @Override
  public List<Vacancy> loadBySalary(SalaryRange range, Pageable page) {
    return Optional.ofNullable(range)
            .filter(r -> r.getFrom() != null)
            .filter(r -> r.getTo() != null)
            .map(r -> repository.findAllBySalaryBetween(r.getFrom(), r.getTo(), page))
            .orElse(Collections.emptyList());
  }

  @Override
  public List<Vacancy> loadByResponsibility(String responsibility, Pageable page) {
    return Optional.ofNullable(responsibility)
            .map(r -> repository.findAllByResponsibilityContainsIgnoreCase(r, page))
            .orElse(Collections.emptyList());
  }

  @Override
  public List<Vacancy> loadByRequirement(String requirement, Pageable page) {
    return Optional.ofNullable(requirement)
            .map(r -> repository.findAllByRequirementContainsIgnoreCase(requirement, page))
            .orElse(Collections.emptyList());
  }

}
