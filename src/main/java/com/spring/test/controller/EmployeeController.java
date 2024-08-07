package com.spring.test.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.exception.ImageProcessingException;
import com.spring.test.excpetion.ResourceNotFoundException;
import com.spring.test.model.Client;
import com.spring.test.model.ClientVerified;
import com.spring.test.repository.EmployeeRespositroy;
import com.spring.test.service.ImageProcessingService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
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
	public ClientVerified creatEmployee(@RequestBody ClientVerified client) {
		return employeeRepository.save(client);
	}
	
	
	// Controller
	@PutMapping("/invoices/{id}")
	public ResponseEntity<ClientVerified> updateInvoice(@PathVariable Long id, @RequestBody ClientVerified invoice) {
		ClientVerified existingInvoice = employeeRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
	    // Update existingInvoice with new data from the request body
		 /* existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
	    existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
	    existingInvoice.setCompany(invoice.getCompany());
	    existingInvoice.setTotalAmount(invoice.getTotalAmount());
	    existingInvoice.setDeliveryAddress(invoice.getDeliveryAddress());
	    // Update other fields similarly
	    */
		 existingInvoice.setNom(invoice.getNom());
		 existingInvoice.setPrenom(invoice.getPrenom());
		 existingInvoice.setSex(invoice.getSex());
		 existingInvoice.setDateNaissance(invoice.getDateNaissance());
		 existingInvoice.setDateDelivre(invoice.getDateDelivre());
		 existingInvoice.setDateExpiration(invoice.getDateExpiration());
		 existingInvoice.setPays(invoice.getPays());
		 existingInvoice.setNumeroPasseport(invoice.getNumeroPasseport());
		
		 ClientVerified updatedInvoice = employeeRepository.save(existingInvoice);
	    return ResponseEntity.ok(updatedInvoice);
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
	  
