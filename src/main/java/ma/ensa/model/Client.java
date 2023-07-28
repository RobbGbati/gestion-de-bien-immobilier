package ma.ensa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty(message="Saisir un nom")
	private String name;
	
	@NotBlank(message="Sasir un email")
	@Email(message="Saisir un email valide")
	private String email;
	
	@Size(min=6,message="Le mot de passe doit avoir 6 caractères au minimum")
	private String password;
	
	private String tel;
	private String profession;
	
	@OneToMany()
	private List<Bien> mesBiens;
	
	@OneToMany()
	private List<AchatLocation> mesAchatsLocations;
	
	@OneToMany()
	private List<CritereRecherche> criteres;

	public List<AchatLocation> getMesAchatsLocations() {
		return mesAchatsLocations;
	}
	public void setMesAchatsLocations(List<AchatLocation> mesAchatsLocations) {
		this.mesAchatsLocations = mesAchatsLocations;
	}
	public List<Bien> getMesBiens() {
		return mesBiens;
	}
	public void setMesBiens(List<Bien> mesBiens) {
		this.mesBiens = mesBiens;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<CritereRecherche> getCriteres() {
		return criteres;
	}
	public void setCriteres(List<CritereRecherche> criteres) {
		this.criteres = criteres;
	}
	
	
	
}
