package com.formflow.meteo.app.infra.entity;

import com.formflow.meteo.app.infra.repository.DocumentTypeRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // テスト後にロールバックされます
class DocumentTypeEntityTest {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Test
    @DisplayName("DocumentTypeEntityを保存し、取得できる")
    void testSaveAndFindDocumentType() {
        // --- 新規作成 ---
        DocumentTypeEntity documentType = new DocumentTypeEntity();
        documentType.setName("有給休暇申請書");
        documentType.setCreatedAt(LocalDateTime.now());

        // --- 保存 ---
        DocumentTypeEntity saved = documentTypeRepository.save(documentType);

        // --- 取得と検証 ---
        DocumentTypeEntity found = documentTypeRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("有給休暇申請書");
        assertThat(found.getCreatedAt()).isNotNull();

        System.out.println("テスト正常終了");
    }
}
