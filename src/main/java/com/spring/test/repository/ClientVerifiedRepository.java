package com.spring.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.test.model.ClientVerified;


public interface ClientVerifiedRepository extends JpaRepository<ClientVerified, Long> {
	
    Optional<ClientVerified> findBySharedId(String sharedId);

	public Optional<ClientVerified> findByNumeroPasseport(String numeroPasseport);

    

}
