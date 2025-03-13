package com.embarkx.jobms.job;

import com.embarkx.jobms.job.clients.CompanyClient;
import com.embarkx.jobms.job.clients.ReviewClient;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;

    private ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
   /* @CircuitBreaker(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")*/
   /* @Retry(name =  "companyBreaker",
            fallbackMethod = "companyBreakerFallback")*/
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
       // return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
        return jobs.stream().map(this::convertToDTOFeign).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("dummy");
        return  list;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job jobEntity = jobOptional.get();
            jobEntity.setTitle(job.getTitle());
            jobEntity.setDescription(job.getDescription());
            jobEntity.setLocation(job.getLocation());
            jobEntity.setMaximumSalary(job.getMaximumSalary());
            jobEntity.setMinimumSalary(job.getMinimumSalary());
            jobRepository.save(jobEntity);
            return jobEntity;
        }
        return null;
    }

    private JobDTO convertToDTO(Job job) {
        //should be used when response type is single object
        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(),
                Company.class);

        // should be used when response type is generic type of collection null specify no request body
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEWMS:8081/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Review>>() {
                });

        List<Review> reviews = reviewResponse.getBody();

        return JobMapper.mapToJobWithCompanyDTO(job, company,reviews);
    }

    private JobDTO convertToDTOFeign(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDTO(job, company,reviews);
    }

}
