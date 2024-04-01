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

import com.spring.test.model.Document;
import com.spring.test.repository.DocumentRepository;
import com.spring.test.service.DocumentServices;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/documents")
public class DocumentController {

	@Autowired
	private    DocumentRepository documentRepository ;

	@Autowired
    private  DocumentServices documentService;



  

    
    @PostMapping("/validate")
    public ResponseEntity<Document> validateDocument(@RequestBody Document document) {
        Document savedDocument = documentService.saveDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    @PostMapping("/reject")
    public ResponseEntity<Document> rejectDocument(@RequestBody Document document) {
        Document savedDocument = documentService.saveDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    // Endpoint to fetch completed documents
    @GetMapping("/completed")
    public List<Document> getCompletedDocuments() {
        return documentService.getCompletedDocuments();
    }
    
    // Endpoint to fetch completed documents
    @GetMapping("/waiting")
    public List<Document> getWaitingDocuments() {
        return documentService.getWaitingDocuments();
    }
	

    
    
    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return documentService.createDocument(document);
    }

    @PutMapping("/{id}")
    public Document updateDocument(@PathVariable Long id, @RequestBody Document document) {
        return documentService.updateDocument(id, document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }
}
