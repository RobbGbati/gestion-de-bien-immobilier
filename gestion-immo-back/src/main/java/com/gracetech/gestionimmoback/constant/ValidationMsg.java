package com.gracetech.gestionimmoback.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationMsg {
	
	public static final String FIRSTNAME_MANDATORY = "Le prenom est obligatoire";
	public static final String LASTNAME_MANDATORY = "Le nom est obligatoire";
	public static final String EMAIL_VALID = "Saisir un email valide";
	public static final String EMAIL_MANDATORY = "L'email est obligatoire";
	public static final String PASSWORD_LENGTH = "Le mot de passe doit avoir 6 caractères au minimum";
}
