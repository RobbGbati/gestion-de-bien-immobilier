package com.gracetech.gestionimmoback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gracetech.gestionimmoback.model.ConfirmToken;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {
	ConfirmToken findByToken(String token);
}
