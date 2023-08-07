package com.gracetech.gestionimmoback.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gracetech.gestionimmoback.model.Client;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@EntityGraph(attributePaths = { "appRole"})
	Client findByUsername(String username);
	
	@EntityGraph(attributePaths = { "appRole"})
	Client findByEmail(String email);
	
	// check if user exists by his username
	boolean existsByUsernameIgnoreCase(String username);
	
	// check if user exists by his email
	boolean existsByEmail(String mail);

    @Query("UPDATE Client c SET c.active = true WHERE c.email =:email")
    @Modifying
	@Transactional
    void activateAccountByEmail(@Param("email") String email);
}
