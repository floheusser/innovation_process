package ch.bpm.innovation.innovation_process.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.bpm.innovation.innovation_process.model.EmployeePoints;
import ch.bpm.innovation.innovation_process.service.PointsService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<Integer> getPoints(@PathVariable String employeeId) {
        int points = pointsService.getPoints(employeeId);
        return ResponseEntity.ok(points);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeePoints>> getAllEmployeePoints() {
        List<EmployeePoints> allPoints = pointsService.getAllEmployeePoints();
        return ResponseEntity.ok(allPoints);
    }
}
