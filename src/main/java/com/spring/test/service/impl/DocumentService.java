package com.spring.test.service.impl;

import java.util.Date;
import java.util.List;

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

	public List<Document> getCompletedDocuments() {
        return documentRepository.findByStatus("Done");
    }
	
	public List<Document> getWaitingDocuments() {
        return documentRepository.findByStatus("Waiting");
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
