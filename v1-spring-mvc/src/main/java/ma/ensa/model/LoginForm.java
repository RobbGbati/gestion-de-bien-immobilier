package ma.ensa.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {

	@NotBlank(message="Sasir un email")
	@Email(message="Saisir un email valide")
	private String email;
	
	@NotBlank(message="Saisir un mot de passe correct")
	@Size(min=6, message="Mot de passe trop court")
	private String password;
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
	public LoginForm(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public LoginForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
