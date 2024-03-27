package com.spring.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.model.Employee;
import java.util.List;

@Repository
public interface EmployeeRespositroy extends JpaRepository<Employee, Long> {


	// Recherche par id
	Employee findById(long id);

	// Vous pouvez également ajouter d'autres méthodes de recherche si nécessaire

}
