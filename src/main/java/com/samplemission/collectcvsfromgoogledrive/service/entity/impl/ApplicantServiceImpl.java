package com.samplemission.collectcvsfromgoogledrive.service.entity.impl;

import com.samplemission.collectcvsfromgoogledrive.model.entity.Applicant;
import com.samplemission.collectcvsfromgoogledrive.service.entity.ApplicantService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class ApplicantServiceImpl implements ApplicantService {
    @Override
    public ResponseEntity<Applicant> save() {
return null;
    }

    @Override
    public ResponseEntity<Applicant> getById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Applicant>> getAll() {
        return null;
    }
}
