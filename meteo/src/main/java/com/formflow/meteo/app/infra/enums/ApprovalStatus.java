package com.formflow.meteo.app.infra.enums;

public enum ApprovalStatus {
    APPROVED("承認"),
    REJECTED("却下"),
    UNAPPROVED("未承認");

    private final String label;

    ApprovalStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
