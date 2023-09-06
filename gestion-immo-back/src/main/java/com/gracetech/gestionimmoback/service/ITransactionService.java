package com.gracetech.gestionimmoback.service;

import java.util.List;

import com.gracetech.gestionimmoback.dto.TransactionDto;

public interface ITransactionService {

    public void save(TransactionDto transactionDto, String currentUserMail);

    public void delete(Long id);

    public List<TransactionDto> getAllTransaction();
    
}
