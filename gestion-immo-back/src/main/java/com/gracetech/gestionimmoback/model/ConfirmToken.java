package com.gracetech.gestionimmoback.model;

import java.util.UUID;

import com.gracetech.gestionimmoback.constant.TableName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TableName.CONFIRM_TOKEN)
public class ConfirmToken extends AbstractAuditing {
	
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "client_id")
	private Client client;

	public ConfirmToken(Client client) {
		super();
		this.client = client;
		this.token = UUID.randomUUID().toString();
	}
}
