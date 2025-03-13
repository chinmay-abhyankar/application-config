package com.embarx.firstjobapp.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company updateCompany(Long id,Company company);
    void createCompany(Company company);
    boolean deleteCompanyById(Long id);
    Company findCompanyById(Long id);
}
