package com.spring.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
