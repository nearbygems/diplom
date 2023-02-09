package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.model.db.Vacancy;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VacancyRepository extends PagingAndSortingRepository<Vacancy, Long> {}
