package com.spring.test.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.test.model.Document;
import com.spring.test.repository.DocumentRepository;
import com.spring.test.service.DocumentServices;

@Service
public class DocumentService implements DocumentServices {

	@Autowired
	private DocumentRepository documentRepository;

	public DocumentService() {

	}

	public DocumentService(DocumentRepository documentRepository1) {
		this.documentRepository = documentRepository1;
	}

	public Document saveDocument(Document document) {
		document.setCreatedAt(new Date());
		document.setUpdatedAt(new Date());
		return documentRepository.save(document);
	}

	// Method to validate a document
	public void validateDocument(String documentId) {
		// Create a new Document object
		Document document = new Document();

		// Set the document ID
		document.setDocumentId(documentId);

		// Set the status to "done"
		document.setStatus("done");

		// Generate a random 5-digit number as the user ID
		Random random = new Random();
		int randomUserId = random.nextInt(90000) + 10000; // Generate a random number between 10000 and 99999
		document.setUserId(String.valueOf(randomUserId));

		// Set created at and updated at timestamps
		document.setCreatedAt(new Date());
		document.setUpdatedAt(new Date());

		// Save the document to the database
		documentRepository.save(document);
	}

	// Method to reject a document
	public void rejectDocument(Long documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new IllegalArgumentException("Document not found"));

		// Update status to "waiting" for rejection
		document.setStatus("waiting");
		documentRepository.save(document);
	}

	// Methods for CRUD operations
	public List<Document> getAllDocuments() {
		return documentRepository.findAll();
	}

	public Document getDocumentById(Long id) {
		return documentRepository.findById(id).orElse(null);
	}

	public Document createDocument(Document document) {
		return documentRepository.save(document);
	}

	public Document updateDocument(Long id, Document document) {
		document.setId(id);
		return documentRepository.save(document);
	}

	public void deleteDocument(Long id) {
		documentRepository.deleteById(id);
	}
}
