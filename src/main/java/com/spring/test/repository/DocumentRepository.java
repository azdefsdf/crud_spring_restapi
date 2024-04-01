package com.spring.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
	
    Document findByDocumentId(String documentId);

}
