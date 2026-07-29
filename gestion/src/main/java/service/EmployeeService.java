package service;


import entity.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final PasswordEncoder passwordEncoder;

    // injection du constructeur
    public EmployeeService(EmployeeRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // 1. Create / Register (Save)
    // ==========================================

    public Employee saveEmployee(Employee employee) {
        // hasher le code avant de save
        String plainPassword = employee.getPassword();
        String hashPassword = passwordEncoder.encode(plainPassword);
        employee.setPassword(hashPassword);
        return repository.save(employee);
    }

    // ==========================================
    // 2. Get All Employees
    // ==========================================

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // ==========================================
    // 3. Get Single Employee by ID
    // ==========================================

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe Non Trouve avec l'ID: " + id));
    }

    // ==========================================
    // 4. Update Employee
    // ==========================================

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        // verifie si l'employé existe
        Employee existingEmployee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe Non Trouve avec l'ID: " + id));

        // met à jour les détails de l'employé
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setRole(employeeDetails.getRole());
        // sauvegarde les modifications
        return repository.save(existingEmployee);
    }

    // ==========================================
    // 5. Delete Single Employee
    // ==========================================
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Employe Non Trouve avec l'ID: " + id);
        }

        repository.deleteById(id);
    }

    // ==========================================
    // 6. Delete Multiple Employees (Batch)
    // ==========================================

    public void deleteMultipleEmployee(List<Long> ids) {
        repository.deleteAllById(ids);
    }


}
