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
import com.formflow.meteo.app.infra.repository.NotificationRepository;

@SpringBootTest
@Transactional // テスト後にロールバックされます
public class NotificationEntityTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("実DBを使ってNotificationEntityが保存できる")
    void testSaveAndFindNotification() {
        // 依存エンティティを先に保存
        EmployeeEntity employee = new EmployeeEntity(null, "佐藤 太郎", "サトウ タロウ", "taro@example.com", "pass1234", 1, 1,
                null, null, LocalDateTime.now());
        employee = employeeRepository.save(employee);

        // NotificationEntityを保存
        NotificationEntity notification = new NotificationEntity();
        notification.setEmployee(employee);
        notification.setDocument(null); // DocumentEntityは必須ではないためnullに設定
        notification.setComment("テストコメント");
        // notification.setType("INFO");
        notification.setMessage("本番DBテスト");
        notification.setStatus("UNREAD");
        notification.setCreatedAt(LocalDateTime.now());

        NotificationEntity saved = notificationRepository.save(notification);

        // 保存されたNotificationEntityを検索・検証
        Optional<NotificationEntity> found = notificationRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStatus()).isEqualTo("UNREAD");
        // assertThat(found.get().getType()).isEqualTo("INFO");
        assertThat(found.get().getMessage()).isEqualTo("本番DBテスト");
        assertThat(found.get().getEmployee().getName()).isEqualTo("佐藤 太郎");

        System.out.println("テスト正常終了");
    }
}