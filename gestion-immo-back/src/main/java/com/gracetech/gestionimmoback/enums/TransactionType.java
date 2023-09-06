package com.gracetech.gestionimmoback.enums;


public enum TransactionType {
    ACHAT("ACHAT"), LOCATION("LOCATION");

    private String description;

    private TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
