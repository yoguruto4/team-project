package com.formflow.meteo.app.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.TravelExpenseTemplateEntity;

@Repository
public interface TravelExpenseTemplateRepository
        extends JpaRepository<TravelExpenseTemplateEntity, Integer>,
        JpaSpecificationExecutor<TravelExpenseTemplateEntity> {

}
