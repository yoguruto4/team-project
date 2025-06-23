package com.formflow.meteo.app.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.DocumentTypeEntity;

@Repository
public interface DocumentTypeRepository
        extends JpaRepository<DocumentTypeEntity, Integer>, JpaSpecificationExecutor<DocumentTypeEntity> {

}
