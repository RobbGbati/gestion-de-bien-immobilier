package com.gracetech.gestionimmoback.dto;

import com.gracetech.gestionimmoback.enums.BienStatus;
import com.gracetech.gestionimmoback.enums.Offre;
import lombok.Data;

import java.io.Serializable;

@Data
public class BienDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String image;
    private String typeBien;
    private String city;
    private String address;
    private String description;
    private BienStatus status;
    private String anciennete;
    private Offre offre;

    private double amount;

    private Long firstOwnerId;
}
