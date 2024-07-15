package com.spring.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
    Optional<Client> findBySharedId(String sharedId);

	public Optional<Client> findByNumeroPasseport(String numeroPasseport);

    

}
