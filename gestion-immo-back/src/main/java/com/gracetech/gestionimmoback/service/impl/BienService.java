package com.gracetech.gestionimmoback.service.impl;

import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.BienMapper;
import com.gracetech.gestionimmoback.model.Bien;
import com.gracetech.gestionimmoback.repository.BienRepository;
import com.gracetech.gestionimmoback.repository.ClientRepository;
import com.gracetech.gestionimmoback.service.IBienService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BienService implements IBienService {

    private final BienRepository bienRepository;
    private final ClientRepository clientRepository;

    @Override
    public BienDto create(BienDto dto) {
        Bien bien = BienMapper.INSTANCE.toEntity(dto);
        if (dto.getFirstOwnerId() != null) {
            bien.setClient(clientRepository.findById(dto.getFirstOwnerId()).orElse(null));
        }
        return BienMapper.INSTANCE.toDto(bienRepository.save(bien));
    }

    @Override
    public Bien getBienById(Long id) {
        return bienRepository.findById(id).orElseThrow( () -> new ElementNotFoundException(Bien.class, id));
    }

    @Override
    public void deleteById(Long id) {
        if (bienRepository.existsById(id)) {
            bienRepository.deleteById(id);
        } else  {
            throw new ElementNotFoundException(Bien.class, id);
        }
    }

    @Override
    public List<Bien> searchBiensByDescription(String query) {
        return bienRepository.searchBiensByDescOrCity(query, PageRequest.of(0, 20));
    }

    @Override
    public List<Bien> findBiensByFirstOwner(Long clientId) {
        return bienRepository.findBiensByFirstOwner(clientId);
    }

    @Override
    public List<Bien> findBienOwnedByClient(Long clienId) {
        return bienRepository.findBiensOwnedByClient(clienId);
    }


}
