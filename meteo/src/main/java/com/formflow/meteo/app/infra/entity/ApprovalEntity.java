package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approval")
    private Integer id;

    @Column(name = "approval_status", nullable = false, length = 20)
    private String status;

    @Column(name = "date_approval_request", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "date_approval")
    private LocalDateTime approvalDate;

}
