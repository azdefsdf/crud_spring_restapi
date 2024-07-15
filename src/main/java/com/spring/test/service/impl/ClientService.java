package com.spring.test.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.test.exception.FileStorageException;
import com.spring.test.excpetion.ResourceNotFoundException;
import com.spring.test.model.Client;
import com.spring.test.model.ClientVerified;
import com.spring.test.repository.ClientRepository;
import com.spring.test.repository.ClientVerifiedRepository;

@Service
public class ClientService {

	private final ClientRepository clientRepository;
	private Client client;
	private Path uploadLocation;
	private static final String IMAGE_DIRECTORY = "C:\\Users\\student\\Desktop\\DocumentProccess";
	private final ClientVerifiedRepository clientVerifiedRepository;
	static {
		ImageIO.scanForPlugins();
		ImageIO.setUseCache(false);
	}

	@Autowired
	public ClientService(ClientRepository clientRepository, Client client,ClientVerifiedRepository clientVerifiedRepository) {
		this.clientRepository = clientRepository;
		this.client = client;
		this.clientVerifiedRepository = clientVerifiedRepository;

		// this.uploadLocation =
		// Paths.get(client.getUploadDir()).toAbsolutePath().normalize();
		this.uploadLocation = Paths.get("C:\\Users\\student\\Desktop\\DocumentProccess").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.uploadLocation);
		} catch (Exception e) {
			throw new FileStorageException("could not creat dir", e);
		}

	}

	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	public List<ClientVerified> getAllClientsVerified() {
		return clientVerifiedRepository.findAll();
	}

	public Client uploadFile(String nom, String prenom, String sex, LocalDate dateDelivre, LocalDate dateExpiration,
			LocalDate dateNaissance, String numeroPasseport, String pays, MultipartFile file) throws IOException {

		// Generate a unique file name
		String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String uniqueFileName = UUID.randomUUID().toString();
		String fileNameWithExtension = uniqueFileName + "." + fileExtension;

		// Save the file to the target location
		Path targetLocation = this.uploadLocation.resolve(fileNameWithExtension);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		// Create a DateTimeFormatter for the desired format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

		// Format the dates
		String formattedDateDelivre = dateDelivre.format(formatter);
		String formattedDateExpiration = dateExpiration.format(formatter);
		String formattedDateNaissance = dateNaissance.format(formatter);

		Client theFile = new Client();
		theFile.setNom(nom);
		theFile.setPrenom(prenom);
		theFile.setSharedId(uniqueFileName);
		theFile.setDateDelivre(formattedDateDelivre); // Set formatted date
		theFile.setDateExpiration(formattedDateExpiration); // Set formatted date
		theFile.setDateNaissance(formattedDateNaissance); // Set formatted date
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

	public List<String> getImageList() throws IOException {
		List<String> imageList = new ArrayList<>();
		Files.walk(Paths.get(IMAGE_DIRECTORY)).filter(Files::isRegularFile).forEach(path -> {
			try {
				File file = path.toFile();
				BufferedImage bufferedImage = ImageIO.read(file);

				if (bufferedImage != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "png", baos);
					String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
					imageList.add(base64Image);
				} else {
					System.err.println("Unable to read image file: " + file.getPath());
				}
			} catch (IOException e) {
				System.err.println("Error processing file: " + path.toString());
				e.printStackTrace();
			}
		});
		return imageList;
	}

	public void processImages(List<String> images) throws IOException {
		// Implement your image processing logic here
		for (String image : images) {
			byte[] decodedBytes = Base64.getDecoder().decode(image.split(",")[1]);
			// Process the decodedBytes as needed
		}
	}

	public Client findClientById(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
	}

	public Optional<Client> getClientByPassportNumber(String numeroPasseport) {
		return clientRepository.findByNumeroPasseport(numeroPasseport);
	}

	public Optional<Client> getClientBySharedId(String sharedId) {
		return clientRepository.findBySharedId(sharedId);
	}

	public Optional<Client> getClientById(Long id) {
		return clientRepository.findById(id);
	}
}
