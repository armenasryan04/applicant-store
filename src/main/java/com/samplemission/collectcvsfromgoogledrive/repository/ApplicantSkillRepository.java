package com.samplemission.collectcvsfromgoogledrive.repository;

import com.samplemission.collectcvsfromgoogledrive.model.entity.ApplicantSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicantSkillRepository extends JpaRepository<ApplicantSkill, UUID> {
}