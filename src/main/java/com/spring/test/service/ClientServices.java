package com.spring.test.service;

import java.util.Optional;

import com.spring.test.model.Client;

public interface ClientServices {
	
	 Client saveClient(Client client);
	  Client findClientById(Long id);
	  public Optional<Client> getClientByPassportNumber(String numeroPasseport);
}
