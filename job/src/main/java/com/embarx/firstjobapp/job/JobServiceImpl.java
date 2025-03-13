package com.embarx.firstjobapp.job;

import com.embarx.firstjobapp.job.Job;
import com.embarx.firstjobapp.job.JobRepository;
import com.embarx.firstjobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
      try {
          jobRepository.deleteById(id);
          return true;
      }catch (Exception e){
          return false;
      }
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
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


}
