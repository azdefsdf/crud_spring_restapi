package com.spring.test.controller;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.excpetion.ResourceNotFoundException;
import com.spring.test.model.Document;
import com.spring.test.repository.DocumentRepository;
import com.spring.test.service.DocumentServices;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class DocumentController {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentServices documentService;

	@PostMapping("/documents/validate")
	public ResponseEntity<Document> validateDocument(@RequestBody Document document) {
		Document savedDocument = documentService.saveDocument(document);
		return ResponseEntity.ok(savedDocument);
	}

	@PostMapping("/documents/reject")
	public ResponseEntity<Document> rejectDocument(@RequestBody Document document) {
		Document savedDocument = documentService.saveDocument(document);
		return ResponseEntity.ok(savedDocument);
	}
	
    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

	// Endpoint to fetch completed documents
	@GetMapping("/documents/completed")
	public List<Document> getCompletedDocuments() {
		return documentService.getCompletedDocuments();
	}

	// Endpoint to fetch completed documents
	@GetMapping("/documents/waiting")
	public List<Document> getWaitingDocuments() {
		return documentService.getWaitingDocuments();
	}

	@PostMapping("/documents/consultjson")
	public ResponseEntity<?> consultDocumentJson(@RequestParam String documentId,Long id) {

		System.out.println("document id : " + documentId);
		try {

			// Assuming documentId is the filename without extension
			String jsonData = documentService.readJsonFile(documentId);

			return ResponseEntity.ok(jsonData);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error consulting document: " + ex.getMessage());
		}
	}

	@PostMapping("/documents/consultpdf")
	public ResponseEntity<?> consultDocumentPdf(@RequestParam String documentId,Long id) {

		System.out.println("document id : " + documentId);
		try {

			// Assuming documentId is the filename without extension
			String pdfPath = documentService.readPdfFilePath(documentId);

			// Process images and get PDF data
			String base64 = documentService.convertPdfToBase64(pdfPath);

			// Create a JSON object with the required structure
			JSONObject jsonResponsePdf = new JSONObject();
			jsonResponsePdf.put("pdfContent", base64);

			return ResponseEntity.ok(jsonResponsePdf.toString());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error consulting document: " + ex.getMessage());
		}
	}

	@PutMapping("/documents/{id}")
	public ResponseEntity<Document> updateDocument(@PathVariable Long id) {
		Document existingDocument = documentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));

		// Assuming you only want to update the status and updatedAt fields
		existingDocument.setStatus("Done"); // Update status to "done"
		existingDocument.setUpdatedAt(new Date()); // Set updatedAt to current date

		Document updatedDocument = documentRepository.save(existingDocument);
		return ResponseEntity.ok(updatedDocument);
	}


	
	
	
	
	
	
	
	//@GetMapping
//	public List<Document> getAllDocuments() {
	//	return documentService.getAllDocuments();
	//}

	@GetMapping("/{id}")
	public Document getDocumentById(@PathVariable Long id) {
		return documentService.getDocumentById(id);
	}

	@PostMapping
	public Document createDocument(@RequestBody Document document) {
		return documentService.createDocument(document);
	}

	@PutMapping("/{id}")
	public Document updateDocumentS(@PathVariable Long id, @RequestBody Document document) {
		return documentService.updateDocuments(id, document);
	}

	@DeleteMapping("/{id}")
	public void deleteDocument(@PathVariable Long id) {
		documentService.deleteDocument(id);
	}
}
