package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.model.Vacancy;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VacancyRepository extends PagingAndSortingRepository<Vacancy, Long> {

  Optional<Vacancy> findByTitle(String title);

}
