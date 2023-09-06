package com.gracetech.gestionimmoback.dto;

import java.io.Serializable;

import com.gracetech.gestionimmoback.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionDto implements Serializable {
    private Long id;
    private Long bienId;
    private String bienDescription;
    private String city;
    private TransactionType type;
}
