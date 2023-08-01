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
public class CritereRecherche implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String parVille;
	private double parPrix;
	
	
	@Temporal(value=TemporalType.DATE)
	private Date date_rech;
	
	public String getParVille() {
		return parVille;
	}
	public void setParVille(String parVille) {
		this.parVille = parVille;
	}
	public double getParPrix() {
		return parPrix;
	}
	public void setParPrix(double parPrix) {
		this.parPrix = parPrix;
	}
	public CritereRecherche() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getDate_rech() {
		return date_rech;
	}
	public void setDate_rech(Date date_rech) {
		this.date_rech = date_rech;
	}
	
	
	
}
