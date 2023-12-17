package com.spring.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.excpetion.ResourceNotFoundException;
import com.spring.test.model.Employee;
import com.spring.test.repository.EmployeeRespositroy;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRespositroy employeeRepository;

	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// creat a rest api for employee
	@PostMapping("/employees")
	public Employee creatEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	// get employe by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("employee not find with id :" + id));
		return ResponseEntity.ok(employee);
	}

	// update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("employee not exist with id : " + id));

		employee.setfirstName(employeeDetails.getfirstName());
		employee.setlastName(employeeDetails.getlastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updaEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updaEmployee);

	}

	@DeleteMapping("/employees/{id}")
    public Boolean deleteEmployee(@PathVariable Long id) {
        try {
            // Attempt to find the employee by id
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

            // If employee is found, delete it
            employeeRepository.delete(employee);

            //return ResponseEntity.ok("Employee deleted successfully");
            return Boolean.TRUE;
        } catch (ResourceNotFoundException ex) {
        	 return false;
           // return ResponseEntity.notFound().build();
        } catch (Exception ex) {
        	return false;
           // return ResponseEntity.status(500).body("Error deleting employee: " + ex.getMessage());
        }
    }

}
