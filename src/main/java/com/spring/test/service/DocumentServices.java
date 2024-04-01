package com.spring.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.test.model.Document;


public interface DocumentServices {
	
	 Document saveDocument(Document document);	
	 void validateDocument(String documentId) ;
	 void rejectDocument(Long documentId);
	 List<Document> getAllDocuments();
	 Document createDocument(Document document);
	 Document getDocumentById(Long id) ;
	 void deleteDocument(Long id);
	 Document updateDocument(Long id, Document document);
	 
}
