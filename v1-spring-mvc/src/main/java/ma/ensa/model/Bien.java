package ma.ensa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bien implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String image;
	private String type_bien; //apartements, chambre, immeuble, maison.....
	private String ville;
	private String adresse;
	private String description;
	private String status;
	private String anciennete; //le local est ancien ou nouveau?
	private String type_offre;//vente ou location
	private double montant;
	
	@Temporal(value=TemporalType.DATE)
	private Date date_ajout;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType_bien() {
		return type_bien;
	}

	public void setType_bien(String type_bien) {
		this.type_bien = type_bien;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(String anciennete) {
		this.anciennete = anciennete;
	}
	
	public String getType_offre() {
		return type_offre;
	}
	public void setType_offre(String type_offre) {
		this.type_offre = type_offre;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	
	public Date getDate_ajout() {
		return date_ajout;
	}

	public void setDate_ajout(Date date_ajout) {
		this.date_ajout = date_ajout;
	}

	public Bien() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
