package kz.satbayev.diplom.repository;

import kz.satbayev.diplom.model.db.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
}
