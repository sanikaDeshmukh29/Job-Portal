package com.jobportal.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id", nullable = false)
    private User seeker;

    @Column(name = "resume_path", length = 500)
    private String resumePath;

    @Column(length = 50)
    private String status = "APPLIED";

    @Column(name = "applied_date")
    private LocalDateTime appliedDate = LocalDateTime.now();
}
