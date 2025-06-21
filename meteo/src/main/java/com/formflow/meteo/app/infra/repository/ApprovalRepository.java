package com.formflow.meteo.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.formflow.meteo.app.infra.entity.ApprovalEntity;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Integer> {
    
    /**
     * 申請書IDで承認記録を検索
     */
    List<ApprovalEntity> findByDocumentIdOrderByApprovedAtDesc(Integer documentId);
    
    /**
     * 承認者IDで承認記録を検索
     */
    @Query("SELECT a FROM ApprovalEntity a WHERE a.approvedBy = :approvedBy ORDER BY a.approvedAt DESC")
    List<ApprovalEntity> findByApprovedByOrderByApprovedAtDesc(@Param("approvedBy") Integer approvedBy);
    
    /**
     * 承認結果で検索
     */
    List<ApprovalEntity> findByApprovalResultOrderByApprovedAtDesc(String approvalResult);
    
    /**
     * 特定の申請書の最新承認記録を取得
     */
    @Query("SELECT a FROM ApprovalEntity a WHERE a.documentId = :documentId ORDER BY a.approvedAt DESC LIMIT 1")
    ApprovalEntity findLatestByDocumentId(@Param("documentId") Integer documentId);
} 