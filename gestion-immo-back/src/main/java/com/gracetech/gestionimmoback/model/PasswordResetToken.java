package com.gracetech.gestionimmoback.model;

import java.time.Instant;
import java.util.Date;

import com.gracetech.gestionimmoback.constant.TableName;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = TableName.PWD_TOKEN_RESET)
public class PasswordResetToken extends AbstractAuditing {
	
	private static final long serialVersionUID = 1L;
	
	// expire dans 5 min
	private static final int EXPIRATION = 5 * 60 * 1000;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String pwdToken;
	
	private Instant expirationDate;
	
	@OneToOne
	@JoinColumn(nullable = false, name = "client_id")
	private Client client;

	public PasswordResetToken(String pwdToken, Client client) {
		super();
		this.pwdToken = pwdToken;
		this.client = client;
		this.expirationDate = new Date(System.currentTimeMillis() + EXPIRATION).toInstant();
	}

}
