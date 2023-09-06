package com.gracetech.gestionimmoback.enums;

public enum Offre {
    VENTE("VENTE"), LOCATION("LOCATION");

    private String desc;

    private Offre(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }
}
