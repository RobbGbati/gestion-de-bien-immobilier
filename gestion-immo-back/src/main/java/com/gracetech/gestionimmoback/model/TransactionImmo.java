package com.gracetech.gestionimmoback.model;

import java.time.Instant;

import com.gracetech.gestionimmoback.constant.TableName;
import com.gracetech.gestionimmoback.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableName.TRANSACTION)
public class TransactionImmo extends AbstractAuditing {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Bien bien;

    private TransactionType type;

    private Instant dateTransaction;
}
