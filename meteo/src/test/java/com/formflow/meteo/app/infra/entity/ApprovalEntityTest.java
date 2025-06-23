package com.formflow.meteo.app.infra.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.formflow.meteo.app.infra.enums.ApprovalStatus;
import com.formflow.meteo.app.infra.repository.ApprovalRepository;
import com.formflow.meteo.app.infra.repository.DocumentRepository;
import com.formflow.meteo.app.infra.repository.DocumentTypeRepository;
import com.formflow.meteo.app.infra.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ApprovalEntityTest {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Test
    void testPersistApprovalEntity() {
        // 申請者（Employee）を保存
        EmployeeEntity applicant = new EmployeeEntity();
        applicant.setName("山田太郎");
        applicant.setNameKana("yamada");
        applicant.setEmail("yamada@example.com");
        applicant.setPassword("pass123");
        applicant.setIdDepartment(1);
        applicant.setAdminAuth(1);
        applicant.setCreatedAt(LocalDateTime.now());
        employeeRepository.save(applicant);

        // ドキュメント種別を保存
        DocumentTypeEntity docType = new DocumentTypeEntity();
        docType.setName("交通費申請書");
        docType.setCreatedAt(LocalDateTime.now());
        documentTypeRepository.save(docType);

        // 承認エンティティを先に保存（Documentで外部キーとして参照するため）
        ApprovalEntity approval = new ApprovalEntity();
        ApprovalStatus status = ApprovalStatus.APPROVED;
        approval.setId(null);
        approval.setStatus(status.getLabel());
        approval.setRequestDate(LocalDateTime.now());
        approval.setApprovalDate(LocalDateTime.now());
        approval = approvalRepository.save(approval);

        // Documentエンティティを作成して保存
        DocumentEntity document = new DocumentEntity();
        document.setApplicant(applicant);
        document.setDocumentType(docType);
        document.setApproval(approval); // Approvalをセット
        document.setSubmissionDate(LocalDateTime.now());
        documentRepository.save(document);

        // 再取得して検証
        ApprovalEntity saved = approvalRepository.findById(approval.getId()).orElseThrow();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo("APP");
    }
}
