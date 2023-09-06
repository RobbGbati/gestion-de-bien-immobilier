package com.gracetech.gestionimmoback.model;

import com.gracetech.gestionimmoback.constant.TableName;
import com.gracetech.gestionimmoback.enums.BienStatus;
import com.gracetech.gestionimmoback.enums.Offre;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TableName.BIEN)
public class Bien extends  AbstractAuditing {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= IDENTITY)
    private long id;

    private String image;
    private String typeBien; //apartements, chambre, immeuble, maison.....
    private String city;
    private String address;
    private String description;
    private BienStatus status;
    private String durabilite; //le local est ancien ou nouveau ou autre?
    private Offre offre;//vente ou location

    @Min(1)
    private double amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "first_owner_id")
    private Client client;
}
