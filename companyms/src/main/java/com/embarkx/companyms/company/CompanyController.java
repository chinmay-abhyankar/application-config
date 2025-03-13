package com.embarkx.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody  Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanyById(@PathVariable Long id, @RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(id, company);
        if (updatedCompany != null)
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
       boolean result =  companyService.deleteCompanyById(id);
       if(result)
           return  ResponseEntity.ok("Company deleted");

       return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id){
        Company company = companyService.findCompanyById(id);
        if(company!=null)
            return  new ResponseEntity<>(company,HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
