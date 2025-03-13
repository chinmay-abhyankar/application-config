package com.embarkx.companyms.company;

import com.embarkx.companyms.company.messaging.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company updateCompany(Long id,Company company);
    void createCompany(Company company);
    boolean deleteCompanyById(Long id);
    Company findCompanyById(Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);
}
