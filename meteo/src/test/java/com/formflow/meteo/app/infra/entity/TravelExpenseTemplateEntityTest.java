package com.formflow.meteo.app.infra.entity;

import com.formflow.meteo.app.infra.repository.EmployeeRepository;
import com.formflow.meteo.app.infra.repository.TravelExpenseTemplateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TravelExpenseTemplateEntityTest {

    @Autowired
    private TravelExpenseTemplateRepository travelExpenseTemplateRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("TravelExpenseTemplateEntityが正しく保存・取得できる")
    void testSaveAndFindTemplate() {
        // 1. まず社員データを用意（テンプレに外部キーとして必要）
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("田中 次郎");
        employee.setNameKana("たなか じろう");
        employee.setEmail("jiro.tanaka@example.com");
        employee.setPassword("jiro123");
        employee.setAdminAuth(0);
        employee.setIdDepartment(1); // 必要なら実在IDをセット
        employee.setCreatedAt(LocalDateTime.now());
        employee = employeeRepository.save(employee);

        // 2. テンプレートエンティティ作成・保存
        TravelExpenseTemplateEntity template = new TravelExpenseTemplateEntity();
        template.setEmployee(employee);
        template.setDeparture("新宿");
        template.setArrival("横浜");
        template.setTransportType("電車");
        template.setTripType("片道");
        template.setRoute("新宿→横浜");
        template.setCost(new BigDecimal("800.00"));
        template.setCreatedAt(LocalDateTime.now());

        TravelExpenseTemplateEntity saved = travelExpenseTemplateRepository.save(template);

        // 3. DBから取得して検証
        Optional<TravelExpenseTemplateEntity> found = travelExpenseTemplateRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDeparture()).isEqualTo("新宿");
        assertThat(found.get().getArrival()).isEqualTo("横浜");
        assertThat(found.get().getTransportType()).isEqualTo("電車");
        assertThat(found.get().getTripType()).isEqualTo("片道");
        assertThat(found.get().getRoute()).isEqualTo("新宿→横浜");
        assertThat(found.get().getCost()).isEqualByComparingTo("800.00");
        assertThat(found.get().getEmployee().getIdEmployee()).isEqualTo(employee.getIdEmployee());
    }
}
