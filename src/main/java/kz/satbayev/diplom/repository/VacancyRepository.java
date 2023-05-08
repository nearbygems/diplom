package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.model.db.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VacancyRepository extends PagingAndSortingRepository<Vacancy, Long> {

    List<Vacancy> findAllByTitleContainsIgnoreCase(String title, Pageable page);

    List<Vacancy> findAllBySalaryBetween(Long from, Long to, Pageable page);

    List<Vacancy> findAllByResponsibilityContainsIgnoreCase(String responsibility, Pageable page);

    List<Vacancy> findAllByRequirementContainsIgnoreCase(String requirement, Pageable page);

}
