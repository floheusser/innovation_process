package ch.bpm.innovation.innovation_process.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.bpm.innovation.innovation_process.model.EmployeePoints;

public interface EmployeePointsRepository extends JpaRepository<EmployeePoints, Long> {
    Optional<EmployeePoints> findByEmployeeId(String employeeId);
}

