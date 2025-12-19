package com.delivery.client.service;

import com.delivery.client.model.Client;
import com.delivery.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        // Préparer un client pour les tests
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
        // Arrange (Préparer)
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        // Act (Exécuter)
        List<Client> result = clientService.getAllClients();

        // Assert (Vérifier)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ahmed", result.get(0).getFirstName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById_Success() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        Client result = clientService.getClientById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ahmed", result.getFirstName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClientById_NotFound() {
        // Arrange
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clientService.getClientById(999L);
        });
        verify(clientRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateClient_Success() {
        // Arrange
        when(clientRepository.findByEmail(client.getEmail()))
                .thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client result = clientService.createClient(client);

        // Assert
        assertNotNull(result);
        assertEquals("Ahmed", result.getFirstName());
        verify(clientRepository, times(1)).findByEmail(client.getEmail());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testCreateClient_EmailAlreadyExists() {
        // Arrange
        when(clientRepository.findByEmail(client.getEmail()))
                .thenReturn(Optional.of(client));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clientService.createClient(client);
        });
        verify(clientRepository, times(1)).findByEmail(client.getEmail());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testUpdateClient() {
        // Arrange
        Client updatedClient = new Client();
        updatedClient.setFirstName("Mohammed");
        updatedClient.setLastName("Alami");
        updatedClient.setEmail("mohammed@test.com");
        updatedClient.setPhone("0698765432");
        updatedClient.setAddress("456 Avenue Test");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client result = clientService.updateClient(1L, updatedClient);

        // Assert
        assertNotNull(result);
        assertEquals("Mohammed", result.getFirstName());
        assertEquals("Alami", result.getLastName());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testDeleteClient() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        // Act
        clientService.deleteClient(1L);

        // Assert
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void testSearchClients() {
        // Arrange
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findByFirstNameContainingOrLastNameContaining(
                "Ahmed", "Ahmed")).thenReturn(clients);

        // Act
        List<Client> result = clientService.searchClients("Ahmed");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clientRepository, times(1))
                .findByFirstNameContainingOrLastNameContaining("Ahmed", "Ahmed");
    }
}