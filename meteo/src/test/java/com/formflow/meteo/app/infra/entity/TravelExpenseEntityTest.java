package com.formflow.meteo.app.infra.entity;

import com.formflow.meteo.app.infra.enums.TripType;
import com.formflow.meteo.app.infra.enums.VehicleType;
import com.formflow.meteo.app.infra.repository.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TravelExpenseEntityTest {

    @Autowired
    private TravelExpenseRepository travelExpenseRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Test
    @DisplayName("TravelExpenseEntity が正しく保存・取得できる")
    void testSaveAndFindTravelExpense() {
        // ① DocumentTypeEntity の作成・保存
        DocumentTypeEntity documentType = new DocumentTypeEntity();
        documentType.setName("交通費申請書");
        documentType.setCreatedAt(LocalDateTime.now());
        documentType = documentTypeRepository.save(documentType);

        // ② EmployeeEntity の作成・保存
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("山田 太郎");
        employee.setNameKana("やまだ たろう");
        employee.setEmail("yamada@example.com");
        employee.setPassword("pass1234");
        employee.setAdminAuth(0);
        employee.setIdDepartment(1);
        employee.setCreatedAt(LocalDateTime.now());
        employee = employeeRepository.save(employee);

        // ApprovalEntity の作成（saveしない！）
        ApprovalEntity approval = new ApprovalEntity();
        approval.setStatus("未承認");
        approval.setRequestDate(LocalDateTime.now());
        approval.setApprovalDate(null);
        approval = approvalRepository.save(approval); // ← これ必須！

        DocumentEntity document = new DocumentEntity();
        document.setDocumentType(documentType);
        document.setApplicant(employee);
        document.setApproval(approval); // save済みapprovalをセット
        document.setSubmissionDate(LocalDateTime.now());
        document = documentRepository.save(document);

        // ⑤ TravelExpenseEntity の作成・保存
        TravelExpenseEntity expense = new TravelExpenseEntity();
        expense.setDocument(document);
        expense.setColumnId(1);
        expense.setVehicleType(VehicleType.電車);
        expense.setDeparture("東京");
        expense.setArrival("大阪");
        expense.setTripType(TripType.片道);
        expense.setRoute("東京→大阪");
        expense.setFare(15000);
        expense.setTravelDate(LocalDateTime.of(2025, 6, 1, 10, 0));
        expense.setFileName("invoice_001.jpg");
        expense.setInvoice(true);
        expense.setCreatedAt(LocalDateTime.now());

        TravelExpenseEntity saved = travelExpenseRepository.save(expense);

        // ⑥ 検証
        Optional<TravelExpenseEntity> found = travelExpenseRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDeparture()).isEqualTo("東京");
        assertThat(found.get().getArrival()).isEqualTo("大阪");
        assertThat(found.get().getFare()).isEqualTo(15000);
        assertThat(found.get().getDocument().getId()).isEqualTo(document.getId());
    }
}
