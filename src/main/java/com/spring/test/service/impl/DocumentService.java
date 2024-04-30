package com.spring.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.exceptions.IOException;
import com.spring.test.config.SamplesConfig;
import com.spring.test.excpetion.CustomException;
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

	public String convertPdfToBase64(String pdfPath) throws Exception {

		File file = new File(pdfPath);

		try (InputStream inputStream = Files.newInputStream(file.toPath())) {
			byte[] bytes = inputStream.readAllBytes();
			String b64 = Base64.getEncoder().encodeToString(bytes);
			return b64;
		}
	}

	public String readFileAsStringAndDelete(String filePath) throws Exception {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
		String fileContent = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

		// Delete the file after reading

		return fileContent;
	}

	private static String combinePaths(String var0, String var1) {
		File var2 = new File(var0);
		File var3 = new File(var2, var1);
		return var3.getPath();
	}
	

	public String readJsonFile(String documentId) throws Exception {
		
		String samplesFolder = SamplesConfig.GetSamplesFolder();
		String jsonFileName = documentId+".json";

		// Construct the path to the file with the custom name
		String customFilePath = combinePaths(samplesFolder,
				"SampleProjects\\Hello\\SampleProject\\Export\\"+jsonFileName);

		if (!customFilePath.isEmpty()) {
		    try {
		        return readFileAsStringAndDelete(customFilePath); // Use jsonFilePath directly
		    } catch (IOException e) {
		        // Handle any file-related exceptions here
		        throw new CustomException("Error handling JSON file: " + customFilePath, e); // Use a custom exception for better handling
		    }
		} else {
		    // Handle the case where the string is empty
		    throw new IllegalArgumentException("JSON file path is empty. Please provide a valid path.");
		}
	}

	
    public String readPdfFilePath(String documentId) throws Exception {
    	
		String samplesFolder = SamplesConfig.GetSamplesFolder();
		String pdfFileName = documentId + ".pdf";
		
		// Construct the path to the file with the custom name
		String customFilePath = combinePaths(samplesFolder,
				"SampleProjects\\Hello\\SampleProject\\saved\\PdfConvert\\" + pdfFileName);

		if (!customFilePath.isEmpty()) {
		    try {
		        return customFilePath; // Use jsonFilePath directly
		    } catch (IOException e) {
		        // Handle any file-related exceptions here
		        throw new CustomException("Error handling pdff file: " + customFilePath, e); // Use a custom exception for better handling
		    }
		} else {
		    // Handle the case where the string is empty
		    throw new IllegalArgumentException("pdf file path is empty. Please provide a valid path.");
		}
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

	public Document updateDocuments(Long id, Document document) {
		document.setId(id);
		return documentRepository.save(document);
	}

	public void deleteDocument(Long id) {
		documentRepository.deleteById(id);
	}
}
