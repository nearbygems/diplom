package kz.satbayev.diplom.builder;

import kz.satbayev.diplom.model.db.Company;
import kz.satbayev.diplom.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCompany")
public class CompanyBuilder implements TestBuilder<Company> {

  private String name = "Satbayev University";
  private String city = "Astana";

  @Override
  public Company build() {

    final var company = new Company();

    company.setName(name);
    company.setCity(city);

    return company;
  }

}
