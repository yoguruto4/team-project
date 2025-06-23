package com.formflow.meteo.app.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository
        extends JpaRepository<EmployeeEntity, Integer>, JpaSpecificationExecutor<EmployeeEntity> {

}
