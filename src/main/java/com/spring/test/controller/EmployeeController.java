package com.spring.test.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.exception.ImageProcessingException;
import com.spring.test.model.Employee;
import com.spring.test.repository.EmployeeRespositroy;
import com.spring.test.service.ImageProcessingService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired(required = false)
	private  EmployeeRespositroy employeeRepository ;


	private final ImageProcessingService imageProcessingService;

	public EmployeeController() {
		super();

		this.imageProcessingService = new ImageProcessingService();
	}

	public EmployeeController(ImageProcessingService imageProcessingService) {
		this.imageProcessingService = imageProcessingService;
	}

	
	// send data to database
	@PostMapping("/invoices")
	public Employee creatEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	

	//processing image to json file and read it 
	@PostMapping("/data")
	public ResponseEntity<?> fetchAbbyyData(@RequestParam("images") MultipartFile images,
			@RequestParam("projectPath") String projectPath) {


			try {
				
				String jsonData = imageProcessingService.processImagesAndGetJsonData(images, projectPath);
				
					return ResponseEntity.ok(jsonData);
				
			} catch (ImageProcessingException ex) {
				// Return ResponseEntity with error message and internal server error status
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
	}
	
	
	//convert image to pdf
	@PostMapping("/pdf")
	public ResponseEntity<?> fetchAbbyyDataPdf(@RequestParam("images") MultipartFile images) {
	    try {
	        // Process images and get PDF data
	        String base = imageProcessingService.convertTIFFtoPDF(images);
	        String base64 = imageProcessingService.convertPdfToBase64(base);
	        
	        String jsonFileName = imageProcessingService.getJsonFileName(); // Call a new method to get the filename

	        // Create a JSON object with the required structure
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("pdfData", base64);
	        jsonResponse.put("filename", jsonFileName);
	        
	        return ResponseEntity.ok(jsonResponse.toString());
	    } catch (ImageProcessingException ex) {
	        // Return ResponseEntity with error message and internal server error status
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    } catch (Exception e) {
	        // Return ResponseEntity with error message and not found status
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}


}
