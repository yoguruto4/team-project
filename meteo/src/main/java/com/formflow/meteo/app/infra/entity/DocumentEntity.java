package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "document")
@Data
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Integer idDocument;

    // 申請書種類との関連（DocumentType）
    @ManyToOne
    @JoinColumn(name = "id_document_type")
    private DocumentTypeEntity documentType;

    // 申請者（Employee）
    @ManyToOne
    @JoinColumn(name = "id_employee")
    private EmployeeEntity employee;

    // 申請者ID（直接参照用）
    @Column(name = "id_employee", insertable = false, updatable = false)
    private Integer employeeId;

    // 承認者ID
    @Column(name = "id_approved_by")
    private Integer approvedBy;

    // ステータス
    @Column(name = "status", nullable = false, length = 20)
    private String status = "申請中";

    // 申請タイトル
    @Column(name = "title", length = 200)
    private String title;

    // 申請内容
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    // 合計金額
    @Column(name = "total_amount")
    private Integer totalAmount;

    // 承認日時
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    // 作成日時
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 申請日時（互換性のため）
    @Column(name = "date_submission")
    private LocalDateTime submissionDate;

}
