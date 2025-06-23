package com.formflow.meteo.app.infra.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document_type")
    private Integer id;

    @Column(name = "nm_document_type", nullable = false, length = 50)
    private String name;

    @Column(name = "document_created_at", nullable = false)
    private LocalDateTime createdAt;

    // DocumentType側からDocumentEntityを呼び出せる
    @OneToMany(mappedBy = "documentType")
    private List<DocumentEntity> documents;
}
