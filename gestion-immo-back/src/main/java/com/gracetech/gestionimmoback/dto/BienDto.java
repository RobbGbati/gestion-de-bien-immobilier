package com.gracetech.gestionimmoback.dto;

import com.gracetech.gestionimmoback.model.Client;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class BienDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String image;
    private String type_bien;
    private String city;
    private String address;
    private String description;
    private String status;
    private String anciennete;
    private String offer;

    private double amount;

    private Long firstOwnerId;
}
