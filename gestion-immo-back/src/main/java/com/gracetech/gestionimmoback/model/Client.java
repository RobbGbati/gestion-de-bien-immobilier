package com.gracetech.gestionimmoback.model;

import com.gracetech.gestionimmoback.constant.TableName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TableName.CLIENT, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@SQLDelete(sql = "UPDATE " + TableName.CLIENT + " SET deleted = true WHERE id = ?")
public class Client extends AbstractAuditing {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
    private String username;
    
    private String lastname;
    
    private String firstname;
    
	private String email;
    
    private String photo;
    
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
	@JoinColumn(name = "role_id" )
	private AppRole appRole;

	@OneToMany()
	@JoinTable(name = "gi_client_biens")
	private Set<Bien> biens = new HashSet<>();

}
