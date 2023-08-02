package com.gracetech.gestionimmoback.model;

import java.time.Instant;

import org.hibernate.annotations.SQLDelete;

import com.gracetech.gestionimmoback.constant.TableName;
import com.gracetech.gestionimmoback.constant.ValidationMsg;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TableName.CLIENT, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@SQLDelete(sql = "UPDATE " + TableName.CLIENT + " SET deleted = 1 WHERE id = ?")
public class Client extends AbstractAuditing {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private String username;
    
    private String lastname;
    
    private String firstname;
    
    @NotBlank(message = ValidationMsg.EMAIL_MANDATORY)
	@Email(message = ValidationMsg.EMAIL_VALID)
	private String email;
    
    private String photo;
    
    @Size(min=6, message = ValidationMsg.PASSWORD_LENGTH)
    private String password;
    
    private String description;
    
	private String tel;
    
	private boolean active = false;
	
	private boolean deleted = false;
	
	@Column(name = "last_connexion_date")
	private Instant lastConnexionDate;
	
	@Column(name = "last_pwd_reset_date")
	private Instant lastPwdResetDate;
	
	@ManyToOne()
	@JoinColumn(name = "id_role" )
	private AppRole appRole;

}
