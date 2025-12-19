package com.delivery.client.repository;

import com.delivery.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    List<Client> findByLastNameContaining(String lastName);

    List<Client> findByFirstNameContainingOrLastNameContaining(
            String firstName, String lastName);
}