package com.spring.test.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Invoice_model")
public class Employee {
	
    @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	   
	  	  		  
	  @Column(name = "InvoiceNumber")
	  private String invoiceNumber;
	   
	  @Column(name = "InvoiceDate")
	  private String invoiceDate;
	   
	  @Column(name = "DeliveryAddress")
	  private String deliveryAddress;
	   
	  @Column(name = "TotalAmount")
	  private Integer totalAmount;
	   
	  @Column(name = "Company")
	  private String company;
	
	public Employee() {
		
		
	}

	public Employee(long id, String invoiceNumber, String invoiceDate, String deliveryAddress, Integer totalAmount,
			String company) {
		super();
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.deliveryAddress = deliveryAddress;
		this.totalAmount = totalAmount;
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
}
