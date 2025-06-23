package com.formflow.meteo.app.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.formflow.meteo.app.infra.enums.TripType;
import com.formflow.meteo.app.infra.enums.VehicleType;

@Entity
@Table(name = "travel_expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_travel_expense")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;

    @Column(name = "id_column", nullable = false)
    private Integer columnId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_vehicle", nullable = false, length = 10)
    private VehicleType vehicleType;

    @Column(name = "place_departure", nullable = false, length = 50)
    private String departure;

    @Column(name = "place_arrival", nullable = false, length = 50)
    private String arrival;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type", nullable = false, length = 10)
    private TripType tripType;

    @Column(name = "route", nullable = false, length = 255)
    private String route;

    @Column(name = "fare", nullable = false)
    private Integer fare;

    @Column(name = "date_travel", nullable = false)
    private LocalDateTime travelDate;

    @Column(name = "nm_file", length = 255)
    private String fileName;

    @Column(name = "invoice", nullable = false)
    private Boolean invoice;

    @Column(name = "travel_created_at", nullable = false)
    private LocalDateTime createdAt;
}
