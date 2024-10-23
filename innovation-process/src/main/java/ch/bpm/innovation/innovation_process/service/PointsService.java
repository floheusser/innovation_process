package ch.bpm.innovation.innovation_process.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.bpm.innovation.innovation_process.model.EmployeePoints;
import ch.bpm.innovation.innovation_process.repository.EmployeePointsRepository;

@Service
public class PointsService {

    @Autowired
    private EmployeePointsRepository pointsRepository;

    public List<EmployeePoints> getAllEmployeePoints() {
        return pointsRepository.findAll();
    }    

    public void addPoints(String employeeId, int points) {
        Optional<EmployeePoints> optionalPoints = pointsRepository.findByEmployeeId(employeeId);
        EmployeePoints employeePoints;

        if (optionalPoints.isPresent()) {
            employeePoints = optionalPoints.get();
            employeePoints.setPoints(employeePoints.getPoints() + points);
        } else {
            employeePoints = new EmployeePoints(employeeId, points);
        }

        pointsRepository.save(employeePoints);
    }

    public int getPoints(String employeeId) {
        return pointsRepository.findByEmployeeId(employeeId)
            .map(EmployeePoints::getPoints)
            .orElse(0);
    }
}

