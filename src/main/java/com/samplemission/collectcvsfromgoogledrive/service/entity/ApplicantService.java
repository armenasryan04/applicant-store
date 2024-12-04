package com.samplemission.collectcvsfromgoogledrive.service.entity;

import com.samplemission.collectcvsfromgoogledrive.model.entity.Applicant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ApplicantService {
ResponseEntity<Applicant> save();
ResponseEntity<Applicant> getById(UUID id);
ResponseEntity<List<Applicant>> getAll();

}
