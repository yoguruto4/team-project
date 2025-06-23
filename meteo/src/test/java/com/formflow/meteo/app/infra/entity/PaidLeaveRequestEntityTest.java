package com.formflow.meteo.app.infra.entity;

import com.formflow.meteo.app.infra.enums.ApprovalStatus;
import com.formflow.meteo.app.infra.repository.ApprovalRepository;
import com.formflow.meteo.app.infra.repository.DocumentRepository;
import com.formflow.meteo.app.infra.repository.DocumentTypeRepository;
import com.formflow.meteo.app.infra.repository.EmployeeRepository;
import com.formflow.meteo.app.infra.repository.PaidLeaveRequestRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // テスト後にロールバックされます
class PaidLeaveRequestEntityTest {

    @Autowired
    private PaidLeaveRequestRepository paidLeaveRequestRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Test
    @DisplayName("PaidLeaveRequestEntityがDocumentに関連付けて保存・取得できる")
    void testSaveAndRetrievePaidLeaveRequest() {
        // --- 申請者・承認者 ---
        EmployeeEntity applicant = new EmployeeEntity();
        applicant.setName("申請 太郎");
        applicant.setNameKana("シンセイ タロウ");
        applicant.setEmail("applicant@example.com");
        applicant.setPassword("pass1234");
        applicant.setAdminAuth(0);
        applicant.setIdDepartment(1);
        applicant.setCreatedAt(LocalDateTime.now());
        applicant = employeeRepository.save(applicant);

        EmployeeEntity approver = new EmployeeEntity();
        approver.setName("承認 花子");
        approver.setNameKana("ショウニン ハナコ");
        approver.setEmail("approver@example.com");
        approver.setPassword("pass5678");
        approver.setAdminAuth(1);
        approver.setIdDepartment(2);
        approver.setCreatedAt(LocalDateTime.now());
        approver = employeeRepository.save(approver);

        // --- 書類種別 ---
        DocumentTypeEntity docType = new DocumentTypeEntity();
        docType.setName("有給休暇申請書");
        docType.setCreatedAt(LocalDateTime.now());
        docType = documentTypeRepository.save(docType);

        // --- 承認データ ---
        ApprovalEntity approval = new ApprovalEntity();
        ApprovalStatus status = ApprovalStatus.APPROVED;
        approval.setId(null);
        approval.setStatus(status.getLabel());
        approval.setRequestDate(LocalDateTime.now());
        approval.setApprovalDate(LocalDateTime.now());
        approval = approvalRepository.save(approval);

        // --- DocumentEntity作成 ---
        DocumentEntity document = new DocumentEntity();
        document.setApplicant(applicant);
        document.setApprovedBy(approver);
        document.setDocumentType(docType);
        document.setApproval(approval);
        document.setSubmissionDate(LocalDateTime.now());
        document = documentRepository.save(document);

        // --- PaidLeaveRequestEntity作成 ---
        PaidLeaveRequestEntity request = new PaidLeaveRequestEntity();
        request.setDocument(document);
        request.setPaidLeaveDays(new BigDecimal("2.0"));
        request.setLeaveReason("家庭の事情");
        request.setDateOfLeave(LocalDateTime.of(2025, 7, 1, 9, 0));
        request.setCreatedAt(LocalDateTime.now());
        request = paidLeaveRequestRepository.save(request);

        // --- 検証 ---
        PaidLeaveRequestEntity found = paidLeaveRequestRepository.findById(request.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getDocument().getId()).isEqualTo(document.getId());
        assertThat(found.getPaidLeaveDays()).isEqualByComparingTo("2.0");
        assertThat(found.getLeaveReason()).isEqualTo("家庭の事情");
        assertThat(found.getDocument().getApplicant().getName()).isEqualTo("申請 太郎");
        assertThat(found.getDocument().getApprovedBy().getEmail()).isEqualTo("approver@example.com");

        System.out.println("テスト正常終了");
    }
}
