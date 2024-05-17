package com.spring.test.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.exception.FileStorageException;
import com.spring.test.model.Client;
import com.spring.test.repository.ClientRepository;

@Service
public class ClientService {

    
    private final ClientRepository clientRepository;
    private Client client;
    private Path uploadLocation;

    
    @Autowired
    public ClientService(ClientRepository clientRepository,Client client) {
        this.clientRepository = clientRepository;
        this.client=client;
        
        this.uploadLocation  = Paths.get(client.getUploadDir()).toAbsolutePath().normalize(); 
        try {
            Files.createDirectories(this.uploadLocation);
        }catch(Exception e){
            throw new FileStorageException("could not creat dir",e);
            }
        
    }



    public  Client uploadFile(String nom,String prenom,String sex,Date dateDelivre,Date dateExpiration,Date dateNaissance,String numeroPasseport,String pays, MultipartFile file) throws IOException {

    	
    	// Générer un nom unique pour le fichier
      //  String uniqueFileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    
    	
    	
        // Extract the file extension from the original file name
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        // Generate a unique ID for the file
        String uniqueFileName = UUID.randomUUID().toString();

        // Append the file extension to the unique file name
        String fileNameWithExtension = uniqueFileName + "." + fileExtension;

        // Create the target location for saving the file
        Path targetLocation = this.uploadLocation.resolve(fileNameWithExtension);

        // Copy the file to the target location
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        
        
        Client theFile = new Client();
        
        theFile.setNom(nom);
        theFile.setPrenom(prenom);
        theFile.setSharedId(uniqueFileName);
        theFile.setDateDelivre(dateDelivre);
        theFile.setDateExpiration(dateExpiration);
        theFile.setDateNaissance(dateNaissance);
        theFile.setSex(sex);
        theFile.setNumeroPasseport(numeroPasseport);
        theFile.setPays(pays);
        theFile.setFile(file.getBytes());
        theFile.setUploadDir(String.valueOf(this.uploadLocation));

        return clientRepository.save(theFile);


    }

    
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
}
