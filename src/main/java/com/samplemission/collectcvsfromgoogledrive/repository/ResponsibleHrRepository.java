package com.samplemission.collectcvsfromgoogledrive.repository;

import com.samplemission.collectcvsfromgoogledrive.model.entity.ResponsibleHr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsibleHrRepository extends JpaRepository<ResponsibleHr, Long> {
    Optional<ResponsibleHr> findByUsernameOrEmail(String username, String email);
}
