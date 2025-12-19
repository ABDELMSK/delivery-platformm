package com.delivery.client.controller;

import com.delivery.client.model.Client;
import com.delivery.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setFirstName("Ahmed");
        client.setLastName("Benali");
        client.setEmail("ahmed@test.com");
        client.setPhone("0612345678");
        client.setAddress("123 Rue Test");
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = Arrays.asList(client);
        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    void testCreateClient() {
        when(clientService.createClient(any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Ahmed", response.getBody().getFirstName());
    }

    @Test
    void testUpdateClient() {
        when(clientService.updateClient(eq(1L), any(Client.class))).thenReturn(client);

        ResponseEntity<Client> response = clientController.updateClient(1L, client);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteClient() {
        ResponseEntity<Void> response = clientController.deleteClient(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).deleteClient(1L);
    }
}