package com.formflow.meteo.app.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paid_leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidLeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paid_leave_request")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;

    @Column(name = "paid_leave_days", nullable = false, precision = 3, scale = 1)
    private BigDecimal paidLeaveDays;

    @Column(name = "leave_reason", nullable = false, length = 255)
    private String leaveReason;

    @Column(name = "date_of_leave", nullable = false)
    private LocalDateTime dateOfLeave;

    @Column(name = "paid_leave_created_at", nullable = false)
    private LocalDateTime createdAt;
}
