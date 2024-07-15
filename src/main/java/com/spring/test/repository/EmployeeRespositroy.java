package com.spring.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.model.Client;
import com.spring.test.model.ClientVerified;

@Repository
public interface EmployeeRespositroy  extends JpaRepository<ClientVerified, Long> {


	// Recherche par id
	ClientVerified findById(long id);

	// Vous pouvez également ajouter d'autres méthodes de recherche si nécessaire

}
