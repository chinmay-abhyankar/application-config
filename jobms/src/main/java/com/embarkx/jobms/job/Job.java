package com.embarkx.jobms.job;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//@Table(name = "aone_job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String minimumSalary;
    private String maximumSalary;
    private String location;

    private Long companyId;

    public Job(){}

    public Job(Long id, String title, String description, String minimumSalary, String maximumSalary, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minimumSalary = minimumSalary;
        this.maximumSalary = maximumSalary;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(String minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public String getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(String maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
