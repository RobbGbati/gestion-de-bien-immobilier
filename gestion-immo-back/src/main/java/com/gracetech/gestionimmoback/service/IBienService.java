package com.gracetech.gestionimmoback.service;

import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.model.Bien;

import java.util.List;

public interface IBienService {

    BienDto create(BienDto bienDto);
    Bien getBienById(Long id);
    void deleteById(Long id);
    List<Bien> searchBiensByDescription(String query);

    List<Bien> findBiensByFirstOwner(Long clientId);

    List<Bien> findBienOwnedByClient(Long clienId);
}
