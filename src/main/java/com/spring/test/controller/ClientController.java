package com.spring.test.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.model.Client;
import com.spring.test.model.ClientVerified;
import com.spring.test.service.ImageProcessingService;
import com.spring.test.service.impl.ClientService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ClientController {

	private final ClientService clientService;
	private final ImageProcessingService employeeService;
	// @PostMapping("/client-add")
	// public Client addClient(@RequestBody Client client) {
	// return clientService.saveClient(client);
	// }
	private static final String IMAGE_DIRECTORY = "C:\\\\\\\\Users\\\\\\\\student\\\\\\\\Desktop\\\\\\\\DocumentProccess";

	public ClientController(ClientService clientService, ImageProcessingService employeeService) {
		this.clientService = clientService;
		this.employeeService = employeeService;
	}

	@PostMapping("/add")
	public ResponseEntity<Client> uploadFile(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom,
			@RequestParam("sex") String sex,
			@RequestParam("dateDelivre") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDelivre,
			@RequestParam("dateExpiration") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateExpiration,
			@RequestParam("dateNaissance") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateNaissance,
			@RequestParam("numeroPasseport") String numeroPasseport, @RequestParam("pays") String pays,
			@RequestParam("file") MultipartFile file) throws IOException {

		// public ResponseEntity<Client> uploadFile(@RequestBody Client client) throws
		// IOException {

		Client theFile = clientService.uploadFile(nom, prenom, sex, dateDelivre, dateExpiration, dateNaissance,
				numeroPasseport, pays, file);

		return new ResponseEntity<>(theFile, HttpStatus.OK);
	}
	
	
	@GetMapping("/clients-all-verified")
    public List<ClientVerified> getAllClientsVerified() {
        return clientService.getAllClientsVerified();
    }
	
	
	
	@GetMapping("/clients-all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

	@PostMapping("/client-add")
	public ResponseEntity<?> addClient(@ModelAttribute Client client, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String directory = "../../src/main/resources/"; // Chemin d'accès où vous souhaitez stocker les images
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(directory + fileName);
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body("Failed to upload image.");
			}
		}

		// Enregistrez le client dans la base de données
		Client savedClient = clientService.saveClient(client);

		return ResponseEntity.ok(savedClient);
	}

	
	  @PostMapping("/process")
	    public ResponseEntity<String> processImages(@RequestBody List<String> images) {
	        try {
	            clientService.processImages(images);
	            return ResponseEntity.ok("Images processed successfully");
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process images");
	        }
	    }
	  
	  
	@GetMapping("/list")
	public ResponseEntity<List<String>> listImages() {
		try {
			List<String> imageList = clientService.getImageList();
			return ResponseEntity.ok(imageList);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/clients-shareid/{sharedId}")
	public ResponseEntity<Client> getClientBySharedId(@PathVariable String sharedId) {
		Optional<Client> client = clientService.getClientBySharedId(sharedId);
		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/clients-passport-id/{numeroPasseport}")
	public ResponseEntity<Client> getClientByPassportNumber(@PathVariable String numeroPasseport) {
		Optional<Client> client = clientService.getClientByPassportNumber(numeroPasseport);
		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/clients-id/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable Long id) {
		Optional<Client> client = clientService.getClientById(id);
		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
