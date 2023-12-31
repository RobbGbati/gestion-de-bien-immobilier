package com.gracetech.gestionimmoback.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Constants {
	

	public class View {
		private View() {}

		public static final String AUTH = "auth";
		public static final String CLIENTS = "api/clients";
		public static final String ROLES = "api/roles";
		public static final String BIENS = "api/biens";
		public static final String CONTACTS = "api/contacts";
		public static final String NOTIFICATIONS = "api/notifications";
		public static final String TRANSACTIONS = "api/transactions";
		
	}
	
	public class Errors {
		private Errors() {}
		
		public static final String EMAIL_TAKEN = "Email is already taken";
	}
	

}
