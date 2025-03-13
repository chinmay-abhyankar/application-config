package com.embarkx.companyms.company;

import com.embarkx.companyms.company.clients.ReviewClient;
import com.embarkx.companyms.company.messaging.ReviewMessage;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
        System.out.println("-- dependency injected ");
    }
    @PostConstruct
    public void initMethod(){

    }

    @PreDestroy
    public void destroyMethod(){

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

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(" review message : " + reviewMessage);
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company not found :"+reviewMessage.getCompanyId()));

        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());

        if(company!=null){
            company.setRating(averageRating);
            companyRepository.save(company);
        }
    }


}
