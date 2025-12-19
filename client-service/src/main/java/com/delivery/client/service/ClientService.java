package com.delivery.client.service;

import com.delivery.client.exception.DuplicateResourceException;
import com.delivery.client.exception.ResourceNotFoundException;
import com.delivery.client.model.Client;
import com.delivery.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
    }

    public Client createClient(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Client", "email", client.getEmail());
        }
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client client = getClientById(id);

        client.setFirstName(clientDetails.getFirstName());
        client.setLastName(clientDetails.getLastName());
        client.setEmail(clientDetails.getEmail());
        client.setPhone(clientDetails.getPhone());
        client.setAddress(clientDetails.getAddress());

        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }

    public List<Client> searchClients(String keyword) {
        return clientRepository
                .findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }
}