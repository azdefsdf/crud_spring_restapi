package com.spring.test.service;

import java.util.List;


import com.spring.test.model.Document;


public interface DocumentServices {
	
	 Document saveDocument(Document document);
	 List<Document> getCompletedDocuments();
	 List<Document> getWaitingDocuments();
	 String convertPdfToBase64(String pdfPath) throws Exception;
	 String readFileAsStringAndDelete(String filePath) throws Exception ;
	 String readJsonFile(String documentId) throws Exception;
	 String readPdfFilePath(String documentId) throws Exception;
	 List<Document> getAllDocuments();
	 Document createDocument(Document document);
	 Document getDocumentById(Long id) ;
	 void deleteDocument(Long id);
	 Document updateDocuments(Long id, Document document);
	 
}
