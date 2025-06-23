package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Integer idEmployee;

    @Column(name = "nm_employee", nullable = false, length = 50)
    private String name;

    @Column(name = "kn_employee", nullable = false, length = 50)
    private String nameKana;

    @Column(name = "mail_address", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 10)
    private String password;

    @Column(name = "admin_auth", nullable = false)
    private Integer adminAuth;

    @Column(name = "id_department", nullable = false)
    private Integer idDepartment;

    // 申請した書類のデータを使用できる
    @OneToMany(mappedBy = "applicant")
    private List<DocumentEntity> appliedDocuments;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private TravelExpenseTemplateEntity template;

    /*
     * @ManyToOne
     *
     * @JoinColumn(name = "id_department", nullable = false)
     * private Department department;
     */

    @Column(name = "employee_created_at", nullable = false)
    private LocalDateTime createdAt;
}
