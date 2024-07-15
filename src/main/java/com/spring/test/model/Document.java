package com.spring.test.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "document_id")
    private String documentId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "updated_at")
    private Date updatedAt;

    
 
    
    // Constructor
    public Document() {
        // Initialize createdAt and updatedAt with the current date and time
       // this.createdAt = new Date();
      //  this.updatedAt = new Date();
    }
    


	public Document(Long id, String documentId, String status, String userId, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.documentId = documentId;
		this.status = status;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

    // Constructors, getters, and setters
    
    
}

