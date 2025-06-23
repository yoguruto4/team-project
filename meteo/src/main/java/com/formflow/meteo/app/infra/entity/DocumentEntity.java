package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Integer id;

    // 申請書種類との関連（DocumentType）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_document_type", nullable = false)
    private DocumentTypeEntity documentType;

    // 申請者（Employee）
    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private EmployeeEntity applicant;

    // 承認対象データ（Approval）※例としてApprovalエンティティが存在する前提
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_approval", nullable = false)
    private ApprovalEntity approval;

    // 承認者（Employee）※自己結合のケース（nullable）
    @ManyToOne
    @JoinColumn(name = "id_approved_by")
    private EmployeeEntity approvedBy;

    @Column(name = "date_submission", nullable = false)
    private LocalDateTime submissionDate;

    // TravelExpenseをDocumentから使用できるようにする
    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TravelExpenseEntity> travelExpenses;

}
