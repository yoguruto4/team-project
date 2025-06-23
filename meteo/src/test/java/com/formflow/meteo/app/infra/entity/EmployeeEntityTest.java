package com.formflow.meteo.app.infra.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.formflow.meteo.app.infra.repository.EmployeeRepository;

@SpringBootTest
@Transactional
public class EmployeeEntityTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("EmployeeEntityが実データベースに保存・取得できる")
    void testSaveAndFindEmployee() {
        // Arrange（テストデータの作成）
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("田中 一郎");
        employee.setNameKana("タナカ イチロウ");
        employee.setEmail("ichiro.tanaka@example.com");
        employee.setPassword("tanaka123");
        employee.setAdminAuth(1);
        employee.setIdDepartment(1);
        employee.setCreatedAt(LocalDateTime.now());

        // Act（保存）
        EmployeeEntity saved = employeeRepository.save(employee);

        // Assert（取得して検証）
        Optional<EmployeeEntity> found = employeeRepository.findById(saved.getIdEmployee());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("田中 一郎");
        assertThat(found.get().getEmail()).isEqualTo("ichiro.tanaka@example.com");

        System.out.println("テスト正常終了");
    }
}
