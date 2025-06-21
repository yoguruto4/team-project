package com.formflow.meteo.app.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    
    /**
     * メールアドレスによるユーザー検索
     * @param email メールアドレス
     * @return EmployeeEntity
     */
    Optional<EmployeeEntity> findByEmail(String email);
    
    /**
     * 管理者権限によるユーザー検索
     * @param adminAuth 管理者権限フラグ
     * @return EmployeeEntityのリスト
     */
    java.util.List<EmployeeEntity> findByAdminAuth(Integer adminAuth);
} 