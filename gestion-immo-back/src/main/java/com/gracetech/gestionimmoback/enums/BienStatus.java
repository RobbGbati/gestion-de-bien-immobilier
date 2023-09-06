package com.gracetech.gestionimmoback.enums;

public enum BienStatus {
    LOUE("LOUE"), ACHETE("ACHETE"), DISPONIBLE("DISPONIBLE");

    private String desc;

    private BienStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
