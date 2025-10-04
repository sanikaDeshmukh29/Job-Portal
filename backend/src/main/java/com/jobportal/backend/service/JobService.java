package com.jobportal.backend.service;

import com.jobportal.backend.dto.JobRequest;
import com.jobportal.backend.dto.JobResponse;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobResponse createJob(JobRequest request, Authentication authentication){

        String recruiterEmail = authentication.getName();

        User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new RuntimeException("Recruiter not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setRecruiter(recruiter);

        Job savedJob = jobRepository.save(job);
        logger.info("Job posted by recruiter: {}", recruiter.getEmail());

        return new JobResponse(
                savedJob.getId(),
                savedJob.getTitle(),
                savedJob.getDescription(),
                savedJob.getLocation(),
                savedJob.getSalary(),
                recruiter.getName(),
                savedJob.getPostedDate()
        );


    }

    public List<JobResponse> getAllJobs(){

        return jobRepository.findAll().stream().map(job -> new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary(),
                job.getRecruiter().getName(),
                job.getPostedDate()
        ))
                .collect(Collectors.toList());
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary(),
                job.getRecruiter().getName(),
                job.getPostedDate()
        );
    }

}
