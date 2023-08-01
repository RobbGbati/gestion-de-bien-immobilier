package ma.ensa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AchatLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Date dateAchatLocation;
	
	@OneToOne
	private Bien bienAcheteLocation;
	
	
	public long getId() {
		return id;
	}
	public Bien getBienAcheteLocation() {
		return bienAcheteLocation;
	}
	public void setBienAcheteLocation(Bien bienAcheteLocation) {
		this.bienAcheteLocation = bienAcheteLocation;
	}

	public Date getDateAchatLocation() {
		return dateAchatLocation;
	}
	public void setDateAchatLocation(Date dateAchatLocation) {
		this.dateAchatLocation = dateAchatLocation;
	}
	public AchatLocation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
