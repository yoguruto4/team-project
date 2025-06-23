package com.formflow.meteo.app.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_expense_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelExpenseTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_template")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "departure_location", nullable = false, length = 255)
    private String departure;

    @Column(name = "arrival_location", nullable = false, length = 255)
    private String arrival;

    @Column(name = "transport_type", nullable = false)
    private String transportType;

    @Column(name = "trip_type", nullable = false)
    private String tripType;

    @Column(name = "route", nullable = false, length = 255)
    private String route;

    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "template_created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
