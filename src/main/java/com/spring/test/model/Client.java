package com.spring.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@ConfigurationProperties(prefix = "file")
@Component
@Table(name="client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String prenom;
    
    @Column(name = "shared_id", unique = true)
    private String sharedId;
    
    @DateTimeFormat(pattern = "dd MMM yyyy")
    private String dateDelivre;
    @DateTimeFormat(pattern = "dd MMM yyyy")
    private String dateExpiration;
    @DateTimeFormat(pattern = "dd MMM yyyy")
    private String dateNaissance;
    private String sex;
    private String numeroPasseport;
    private String pays;
    @Lob
    private byte[] file;
    @Column(name = "upload_dir")
    private String uploadDir;

    @OneToOne
    @JoinColumn(name="client_verified_id")
    private ClientVerified clientverified;



	public Client(long id, String nom, String prenom, String sharedId, String dateDelivre, String dateExpiration,
			String dateNaissance, String sex, String numeroPasseport, String pays, byte[] file, String uploadDir) {
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
	
	


	public void setId(long id) {
		this.id = id;
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


	public String  getDateDelivre() {
		return dateDelivre;
	}


	public void setDateDelivre(String  dateDelivre) {
		this.dateDelivre = dateDelivre;
	}


	public String  getDateExpiration() {
		return dateExpiration;
	}


	public void setDateExpiration(String  dateExpiration) {
		this.dateExpiration = dateExpiration;
	}


	public String  getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(String  dateNaissance) {
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
