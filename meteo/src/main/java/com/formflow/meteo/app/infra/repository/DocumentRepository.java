package com.formflow.meteo.app.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.DocumentEntity;

@Repository
public interface DocumentRepository
        extends JpaRepository<DocumentEntity, Integer>, JpaSpecificationExecutor<DocumentEntity> {

}
