package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "approval")
@Data
public class ApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approval")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;

    @Column(name = "approval_status", nullable = false, length = 20)
    private String status;

    @Column(name = "date_approval_request", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "date_approval")
    private LocalDateTime approvalDate;

}
