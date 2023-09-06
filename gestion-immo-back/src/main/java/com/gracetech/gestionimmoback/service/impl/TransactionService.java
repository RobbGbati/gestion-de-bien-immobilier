package com.gracetech.gestionimmoback.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracetech.gestionimmoback.dto.TransactionDto;
import com.gracetech.gestionimmoback.enums.BienStatus;
import com.gracetech.gestionimmoback.enums.TransactionType;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.TransactionMapper;
import com.gracetech.gestionimmoback.model.Bien;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.model.TransactionImmo;
import com.gracetech.gestionimmoback.repository.BienRepository;
import com.gracetech.gestionimmoback.repository.ClientRepository;
import com.gracetech.gestionimmoback.repository.TransactionImmoRepository;
import com.gracetech.gestionimmoback.service.ITransactionService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionImmoRepository transactionRepository;
    private final BienRepository bienRepository;
    private final ClientRepository clientRepository;

    @Override
    public void save(TransactionDto transactionDto, String currentUsername) {
        if (transactionDto.getBienId() != null) {
            Bien bien = bienRepository.findById(transactionDto.getBienId()).orElseThrow(() -> new ElementNotFoundException(Bien.class));
            TransactionImmo transaction = new TransactionImmo();
            transaction.setBien(bien);
            transaction.setDateTransaction(Instant.now());
            transaction.setType(transactionDto.getType());
            transaction = transactionRepository.save(transaction);

            // mettre à jour les biens du client faisant la transaction
            Client client = clientRepository.findByUsername(currentUsername);
            client.getTransactions().add(transaction);
            clientRepository.save(client);

            // mettre à jour le status du bien
            setBienStatus(transactionDto.getType(), bien);
            bienRepository.save(bien);

        }
    }

    @Override
    public void delete(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            throw new ElementNotFoundException(TransactionImmo.class, id);
        }
    }

    @Override
    public List<TransactionDto> getAllTransaction() {
        List<TransactionImmo> list = transactionRepository.findAll();
        return TransactionMapper.INSTANCE.toDto(list);
    }

    private void setBienStatus(TransactionType type, Bien bien) {
        if (type.getDescription().equals("ACHAT")) {
            bien.setStatus(BienStatus.ACHETE);
        } else if (type.getDescription().equals("LOCATION")) {
            bien.setStatus(BienStatus.LOUE);
        }
    }
    
}
