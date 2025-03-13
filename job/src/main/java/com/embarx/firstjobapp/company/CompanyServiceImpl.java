package com.embarx.firstjobapp.company;

import com.embarx.firstjobapp.job.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Long id,Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()){
            Company companyEntity = companyOptional.get();
            companyEntity.setDescription(company.getDescription());
            companyEntity.setName(company.getName());
            companyEntity.setJobs(company.getJobs());
            companyEntity = companyRepository.save(companyEntity);
            return companyEntity;
        }
        return null;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        boolean exist = companyRepository.existsById(id);
        if(exist){
            companyRepository.deleteById(id);
            return  true;
        }
    return  false;
    }

    @Override
    public Company findCompanyById(Long id) {
        Optional<Company> companyExist = companyRepository.findById(id);
        return companyExist.orElse(null);
    }


}
