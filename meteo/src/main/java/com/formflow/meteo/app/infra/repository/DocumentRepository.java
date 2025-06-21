package com.formflow.meteo.app.infra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {
    
    /**
     * 全申請書を作成日時の降順で取得
     */
    List<DocumentEntity> findAllByOrderByCreatedAtDesc();
    
    /**
     * ステータスで検索（作成日時降順）
     */
    List<DocumentEntity> findByStatusOrderByCreatedAtDesc(String status);
    
    /**
     * 申請書タイプで検索（作成日時降順）
     */
    @Query("SELECT d FROM DocumentEntity d WHERE d.documentType.typeName = :type ORDER BY d.createdAt DESC")
    List<DocumentEntity> findByDocumentTypeOrderByCreatedAtDesc(@Param("type") String type);
    
    /**
     * 従業員IDで検索（作成日時降順）
     */
    @Query("SELECT d FROM DocumentEntity d WHERE d.employee.idEmployee = :employeeId ORDER BY d.createdAt DESC")
    List<DocumentEntity> findByEmployeeIdOrderByCreatedAtDesc(@Param("employeeId") Integer employeeId);
    
    /**
     * 複数ステータスで検索（作成日時降順）
     */
    List<DocumentEntity> findByStatusInOrderByCreatedAtDesc(List<String> statuses);
    
    /**
     * ステータス別件数
     */
    long countByStatus(String status);
    
    /**
     * 期間別件数
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 従業員別の申請書件数
     */
    @Query("SELECT COUNT(d) FROM DocumentEntity d WHERE d.employee.idEmployee = :employeeId")
    long countByEmployeeId(@Param("employeeId") Integer employeeId);
    
    /**
     * 今月の申請書件数
     */
    @Query("SELECT COUNT(d) FROM DocumentEntity d WHERE YEAR(d.createdAt) = :year AND MONTH(d.createdAt) = :month")
    long countByYearAndMonth(@Param("year") int year, @Param("month") int month);
} 