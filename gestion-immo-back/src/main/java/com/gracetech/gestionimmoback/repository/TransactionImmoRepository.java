package com.gracetech.gestionimmoback.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gracetech.gestionimmoback.model.TransactionImmo;

@Repository
public interface TransactionImmoRepository extends CrudRepository<TransactionImmo, Long> {
    List<TransactionImmo> findAll();
}
