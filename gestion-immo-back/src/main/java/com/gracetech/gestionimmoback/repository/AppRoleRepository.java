package com.gracetech.gestionimmoback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gracetech.gestionimmoback.model.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	
	AppRole findByDescription(String description);

}
