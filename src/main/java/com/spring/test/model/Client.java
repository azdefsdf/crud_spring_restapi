package com.spring.test.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@ConfigurationProperties(prefix = "file")
@Component
@Table(name="client_file")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String sharedId;
	private Date dateDelivre;
    private Date dateExpiration;
    private Date dateNaissance;
    private String sex;
    private String numeroPasseport;
    private String pays;
    @Lob
    private  byte[] file;
    @Column(name="upload_dir")
    private String uploadDir;
    
    
    
    public Client(Long id, String nom, String prenom, String sharedId, Date dateDelivre, Date dateExpiration,
    		Date dateNaissance, String sex, String numeroPasseport, String pays, byte[] file, String uploadDir) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.sharedId = sharedId;
		this.dateDelivre = dateDelivre;
		this.dateExpiration = dateExpiration;
		this.dateNaissance = dateNaissance;
		this.sex = sex;
		this.numeroPasseport = numeroPasseport;
		this.pays = pays;
		this.file = file;
		this.uploadDir = uploadDir;
	}





	public String getSharedId() {
		return sharedId;
	}

	public void setSharedId(String sharedId) {
		this.sharedId = sharedId;
	}

	public byte[] getFile() {
		return file;
	}


	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}


	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

    

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public Date getDateDelivre() {
		return dateDelivre;
	}


	public void setDateDelivre(Date dateDelivre) {
		this.dateDelivre = dateDelivre;
	}


	public Date getDateExpiration() {
		return dateExpiration;
	}


	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}


	public Date getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getNumeroPasseport() {
		return numeroPasseport;
	}


	public void setNumeroPasseport(String numeroPasseport) {
		this.numeroPasseport = numeroPasseport;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}
	
	

    // Getters and setters
    
}
