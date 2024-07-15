package com.spring.test.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abbyy.FCEngine.Engine;
import com.abbyy.FCEngine.ExportDestinationTypeEnum;
import com.abbyy.FCEngine.FileExportFormatEnum;
import com.abbyy.FCEngine.IBatch;
import com.abbyy.FCEngine.IEngine;
import com.abbyy.FCEngine.IEngineLoader;
import com.abbyy.FCEngine.IExportParams;
import com.abbyy.FCEngine.IFileExportParams;
import com.abbyy.FCEngine.IProject;
import com.abbyy.FCEngine.MessagesLanguageEnum;
import com.abbyy.FCEngine.RecognitionModeEnum;
import com.aspose.imaging.Image;
import com.aspose.imaging.fileformats.tiff.TiffImage;
import com.aspose.imaging.imageoptions.PdfOptions;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.spring.test.config.SamplesConfig;
import com.spring.test.exception.ImageProcessingException;
import com.spring.test.excpetion.ResourceNotFoundException;
import com.spring.test.model.Client;
import com.spring.test.model.ClientVerified;
import com.spring.test.repository.EmployeeRespositroy;

@Service
public class ImageProcessingService {

	@Autowired
	private EmployeeRespositroy employeeRespositroy;
	// Member variable to store the renamed filename
	String renamedJsonFilename;
	String renamedFilename = UUID.randomUUID().toString();

	public String processImagesAndGetJsonData(MultipartFile images, String projectPath)
			throws ImageProcessingException {
		IEngine engine = null;
		String jsonData = null;

		try {

			// Getting engine loader
			trace("Getting engine loader...");
			IEngineLoader engineLoader = Engine.GetEngineLoader(SamplesConfig.GetDllPath());

			// Setting parameters to load the engine
			trace("Setting parameters to load the engine...");
			engineLoader.setCustomerProjectId(SamplesConfig.GetCustomerProjectId());
			engineLoader.setLicensePath(SamplesConfig.GetLicensePath());
			engineLoader.setLicensePassword(SamplesConfig.GetLicensePassword());

			// Load the engine
			trace("Load the engine...");
			engine = engineLoader.GetEngine();
			// Set English language for any messages (e.g., in logs)
			engine.setMessagesLanguage(MessagesLanguageEnum.ML_English);

			// Opening the project
			String samplesFolder = SamplesConfig.GetSamplesFolder();
			trace("Opening the project...");
			String projectFilePath = combinePaths(samplesFolder, projectPath);
			trace("Opening the project: " + projectFilePath);
			IProject project = engine.OpenProject(projectFilePath);

			// Remove any batches that could remain from previous runs of FC SDK samples
			project.getBatches().DeleteAll();

			// Creating a batch
			trace("Creating a batch...");
			IBatch batch = project.getBatches().AddNew();
			// Opening the batch
			trace("Opening the batch...");
			batch.Open();

			try {

				// Adding images to the batch
				trace("Adding images...");
				// Assuming images is now just a single MultipartFile
				MultipartFile image = images;
				// Convert MultipartFile to File
				File imageFile = convertMultipartFileToFile(image);

				// Add image to batch processing
				batch.AddImage(imageFile.getPath());

				// Recognizing the images
				trace("Recognizing the images...");
				batch.Recognize(null, RecognitionModeEnum.RM_ReApplyDocumentDefinitions, null);

				trace("Exporting... format Json");
				IExportParams exportParamsJSON = engine.CreateExportParams(ExportDestinationTypeEnum.EDT_File);
				IFileExportParams fileExportParamsJSON = exportParamsJSON.getFileExportParams();
				fileExportParamsJSON
						.setRootPath(combinePaths(samplesFolder, "SampleProjects\\Hello\\SampleProject\\Export"));
				fileExportParamsJSON.setFileFormat(FileExportFormatEnum.FEF_JSON); // Adjust as needed
				project.Export(null, exportParamsJSON);

				renamedJsonFilename = renameFile();
				jsonData = readFileAsStringAndDelete(renamedJsonFilename);

				trace("Export done successfully.");
			} catch (Exception ex) {
				trace("Export error :." + ex.getMessage());
			} finally {
				// Closing the batch and the project
				trace("Closing the batch and the project...");
				batch.Close();
				project.Close();
			}
		} catch (Exception ex) {
			throw new ImageProcessingException("Error processing images", ex);
		} finally {
			// Deinitializing Engine
			trace("Deinitializing Engine...");
			if (engine != null) {
				Engine.Unload();
			}
			trace("==================================");

			trace(" waiting For PDF Converter ... ");

			trace("==================================");
			// Reset renamedFilename to null

		}

		return jsonData;
	}

	private static void trace(String txt) {
		System.out.println(txt);
	}

	private static String combinePaths(String var0, String var1) {
		File var2 = new File(var0);
		File var3 = new File(var2, var1);
		return var3.getPath();
	}

	public static File convertMultipartFileToFile(MultipartFile multipartFile) throws Exception {
		String originalFilename = multipartFile.getOriginalFilename();
		String filePath = "C:\\ProgramData\\ABBYY\\FCSDK\\12\\FlexiCapture SDK\\Samples\\SampleProjects\\Hello\\SampleProject\\saved\\"
				+ originalFilename;
		File file = new File(filePath);
		multipartFile.transferTo(file);
		return file;
	}

	private String renameFile() throws Exception {

		String samplesFolder = SamplesConfig.GetSamplesFolder();
		String customFileName = renamedFilename + ".json";

		// Construct the path to the exported JSON file
		String exportedFilePath = combinePaths(samplesFolder,
				"SampleProjects\\Hello\\SampleProject\\Export\\Batch.json");

		// Construct the path to the file with the custom name
		String customFilePath = combinePaths(samplesFolder,
				"SampleProjects\\Hello\\SampleProject\\Export\\" + customFileName);

		// Rename the exported JSON file to the custom name
		File exportedFile = new File(exportedFilePath);
		File customFile = new File(customFilePath);
		boolean renamed = exportedFile.renameTo(customFile);
		if (renamed) {
			System.out.println("Renamed the file from 'Batch.json' to '" + customFileName + "'");
			return customFilePath; // Return the new file path with the custom name
		} else {
			System.out.println("Failed to rename the file");
			return null; // Return null if renaming failed
		}
	}

	private static String readFileAsStringAndDelete(String filePath) throws Exception {

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


	public String convertTIFFtoPDF(MultipartFile images) throws Exception {
		MultipartFile image = images;
		File imageFile = convertMultipartFileToFile(image);

		// Extract file name and parent folder
		String fileName = imageFile.getName();
		String parentFolderPath = imageFile.getParentFile().getPath();

		// Create a new folder for PDF conversion
		String pdfConvertFolderPath = parentFolderPath + File.separator + "PdfConvert";
		File pdfConvertFolder = new File(pdfConvertFolderPath);
		if (!pdfConvertFolder.exists()) {
			pdfConvertFolder.mkdirs(); // Create the folder if it doesn't exist
		}

		// Load the TIFF image
		InputStream inputStream = new FileInputStream(imageFile);
		TiffImage tiffImage = (TiffImage) Image.load(inputStream);

		// Create PDF options
		PdfOptions pdfOptions = new PdfOptions();

		// Save the TIFF image as a PDF
		File outputPdfFile = new File(pdfConvertFolderPath, fileName.replaceFirst("[.][^.]+$", "") + ".pdf");
		FileOutputStream outputStream = new FileOutputStream(outputPdfFile);
		tiffImage.save(outputStream, pdfOptions);
		outputStream.close();

		// Rename the pdf file
		File renamedPdfFile = new File(outputPdfFile.getParent(), renamedFilename + ".pdf");
		if (outputPdfFile.renameTo(renamedPdfFile)) {
			trace("Pdf renamed successfully to :" + renamedFilename + ".pdf");
		} else {
			trace("Failed to rename Pdf.");
		}
		// Close the input stream
		inputStream.close();
		System.out.println("PDF created successfully at: " + renamedPdfFile.getAbsolutePath());
		trace("====================");
		trace("The End ! ");
		trace("====================");
		// Reset renamedFilename to null
		renamedFilename =UUID.randomUUID().toString();
		// Return the file path of the generated PDF file
		return renamedPdfFile.getAbsolutePath();
	}

	public String convertImageToPdf(MultipartFile images) throws Exception {

		MultipartFile image = images;

		File imageFile = convertMultipartFileToFile(image);

		// Extract file name and parent folder
		String fileName = imageFile.getName();
		String parentFolderPath = imageFile.getParentFile().getPath(); // Using getPath()

		// Create a new folder for PDF conversion
		String pdfConvertFolderPath = parentFolderPath + File.separator + "PdfConvert";
		File pdfConvertFolder = new File(pdfConvertFolderPath);
		if (!pdfConvertFolder.exists()) {
			pdfConvertFolder.mkdirs(); // Create the folder if it doesn't exist
		}

		// Create a new PDF document
		Document doc = new Document();
		DocumentBuilder builder = new DocumentBuilder(doc);

		// Insert the image into the document
		builder.insertImage(imageFile.getPath());

		// Save the output PDF file inside the "PdfConvert" folder
		String outputFileName = fileName.replaceFirst("[.][^.]+$", "") + ".pdf";
		String outputPath = pdfConvertFolderPath + File.separator + outputFileName;
		doc.save(outputPath);

		System.out.println("PDF created successfully at: " + outputPath);
		trace("====================");
		trace("The End ! ");
		trace("====================");
		return outputPath;

	}

	public String convertPdfToBase64(String pdfPath) throws Exception {

		File file = new File(pdfPath);

		try (InputStream inputStream = Files.newInputStream(file.toPath())) {
			byte[] bytes = inputStream.readAllBytes();
			String b64 = Base64.getEncoder().encodeToString(bytes);
			return b64;
		}
	}

	public String getJsonFileName() throws Exception {
		if (renamedJsonFilename == null) {
			throw new Exception("No renamed filename available"); // Handle missing filename
		}

		// Extract the filename from the full path
		File file = new File(renamedJsonFilename);
		String filenameWithoutExtension = file.getName();

		// Remove the ".json" extension (if present)
		int dotPos = filenameWithoutExtension.lastIndexOf('.');
		if (dotPos > 0) {
			filenameWithoutExtension = filenameWithoutExtension.substring(0, dotPos);
		}

		return filenameWithoutExtension;
	}
    public ClientVerified findClientById(Long id) {
        return employeeRespositroy.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
    }
}