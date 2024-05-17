package com.spring.test.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.model.Client;
import com.spring.test.service.impl.ClientService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ClientController {


    private final ClientService clientService;

   // @PostMapping("/client-add")
   // public Client addClient(@RequestBody Client client) {
  //      return clientService.saveClient(client);
  //  }
    
    
    public ClientController (ClientService clientService){
        this.clientService = clientService;
    }
    

    
    @PostMapping("/add")
      public ResponseEntity<Client> uploadFile(@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("sex") String sex,@RequestParam("dateDelivre") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDelivre,@RequestParam("dateExpiration") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateExpiration,@RequestParam("dateNaissance")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateNaissance,@RequestParam("numeroPasseport") String numeroPasseport,@RequestParam("pays") String pays,@RequestParam("file") MultipartFile file) throws IOException {

  // public ResponseEntity<Client> uploadFile(@RequestBody Client client) throws IOException {
    	
    	
        Client theFile = clientService.uploadFile(nom,prenom,sex, dateDelivre, dateExpiration, dateNaissance, sex, numeroPasseport,file);


        return new ResponseEntity<>(theFile, HttpStatus.OK);
    }
    
    
    @PostMapping("/client-add")
    public ResponseEntity<?> addClient(@ModelAttribute Client client, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String directory = "src/main/resources/"; // Chemin d'accès où vous souhaitez stocker les images
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
}

