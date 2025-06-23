package com.formflow.meteo.app.infra.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.formflow.meteo.app.infra.enums.ApprovalStatus;
import com.formflow.meteo.app.infra.repository.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DocumentEntityTest {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TravelExpenseRepository travelExpenseRepository;

    @Test
    public void testPersistDocumentEntity() {
        // --- 依存エンティティの保存 ---
        DocumentTypeEntity docType = new DocumentTypeEntity();
        docType.setName("AAA");
        docType.setCreatedAt(LocalDateTime.now());
        documentTypeRepository.save(docType);

        EmployeeEntity applicant = new EmployeeEntity(null, "佐藤太郎", "さとうたろう", "taro@example.com", "pass", 1, 1, null,
                null, LocalDateTime.now());
        EmployeeEntity approver = new EmployeeEntity(null, "鈴木次郎", "すずきじろう", "jiro@example.com", "pass", 1, 1, null,
                null, LocalDateTime.now());
        employeeRepository.save(applicant);
        employeeRepository.save(approver);

        ApprovalEntity approval = new ApprovalEntity();
        ApprovalStatus status = ApprovalStatus.REJECTED;
        approval.setStatus(status.getLabel());
        approval.setRequestDate(LocalDateTime.now());
        approval.setApprovalDate(null);
        approvalRepository.save(approval);

        // --- DocumentEntityの作成 ---
        DocumentEntity document = new DocumentEntity();
        document.setDocumentType(docType);
        document.setApplicant(applicant);
        document.setApproval(approval);
        document.setApprovedBy(approver);
        document.setSubmissionDate(LocalDateTime.now());

        // 空の TravelExpense を設定（後から追加可）
        document.setTravelExpenses(Collections.emptyList());

        DocumentEntity saved = documentRepository.save(document);

        // --- アサーション ---
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDocumentType().getName()).isEqualTo("AAA");
        assertThat(saved.getApplicant().getName()).isEqualTo("佐藤太郎");
        assertThat(saved.getApproval().getStatus()).isEqualTo("却下");
        assertThat(saved.getApprovedBy().getName()).isEqualTo("鈴木次郎");
        assertThat(saved.getTravelExpenses()).isEmpty();
    }
}
