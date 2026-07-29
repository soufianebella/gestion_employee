package controller;

import entity.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EmployeeService;

@RestController
@RequestMapping("/api/employees") // URL de base pour tous les methodes
public class EmployeeController {

    public final EmployeeService service;

    // injection ddu constructeur
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // ==========================================
    // 1. Create/Register Employee (POST)
    // ==========================================

    @PostMapping("/register")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = service.saveEmployee(employee);
        // retourne le statut 201
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

    }
}
