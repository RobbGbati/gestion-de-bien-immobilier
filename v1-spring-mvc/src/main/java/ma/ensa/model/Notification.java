package ma.ensa.model;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Notification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String contenuNotifcation;
	
	@OneToOne(cascade=CascadeType.ALL)
	private CritereRecherche mesCriteres;
	

	public CritereRecherche getMesCriteres() {
		return mesCriteres;
	}

	public void setMesCriteres(CritereRecherche mesCriteres) {
		this.mesCriteres = mesCriteres;
	}

	public String getContenuNotifcation() {
		return contenuNotifcation;
	}

	public void setContenuNotifcation(String contenuNotifcation) {
		this.contenuNotifcation = contenuNotifcation;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
