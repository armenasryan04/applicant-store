package com.samplemission.collectcvsfromgoogledrive.repository;

import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicantExperienceRepository extends JpaRepository<ApplicantExperience, UUID> {
}
