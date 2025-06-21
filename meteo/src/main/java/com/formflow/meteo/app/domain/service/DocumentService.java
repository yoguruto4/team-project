package com.formflow.meteo.app.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formflow.meteo.app.infra.entity.ApprovalEntity;
import com.formflow.meteo.app.infra.entity.DocumentEntity;
import com.formflow.meteo.app.infra.repository.ApprovalRepository;
import com.formflow.meteo.app.infra.repository.DocumentRepository;

@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    /**
     * 全ての申請書を取得
     * @return 申請書リスト
     */
    public List<DocumentEntity> findAllApplications() {
        return documentRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * ステータスで申請書を検索
     * @param status ステータス
     * @return 申請書リスト
     */
    public List<DocumentEntity> findByStatus(String status) {
        return documentRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    /**
     * 申請書タイプで検索
     * @param type 申請書タイプ
     * @return 申請書リスト
     */
    public List<DocumentEntity> findByType(String type) {
        return documentRepository.findByDocumentTypeOrderByCreatedAtDesc(type);
    }

    /**
     * 申請書をIDで検索
     * @param id 申請書ID
     * @return 申請書エンティティ
     */
    public DocumentEntity findById(Integer id) {
        Optional<DocumentEntity> document = documentRepository.findById(id);
        return document.orElse(null);
    }

    /**
     * 従業員の申請書を取得
     * @param employeeId 従業員ID
     * @return 申請書リスト
     */
    public List<DocumentEntity> findByEmployeeId(Integer employeeId) {
        return documentRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId);
    }

    /**
     * 完了済み申請書を取得
     * @return 完了済み申請書リスト
     */
    public List<DocumentEntity> findCompletedApplications() {
        return documentRepository.findByStatusInOrderByCreatedAtDesc(List.of("承認", "却下"));
    }

    /**
     * 申請を承認
     * @param documentId 申請書ID
     * @param approvedBy 承認者ID
     * @param comment コメント
     */
    public void approveApplication(Integer documentId, Integer approvedBy, String comment) {
        DocumentEntity document = documentRepository.findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException("申請書が見つかりません"));
        
        // 申請書のステータスを更新
        document.setStatus("承認");
        document.setApprovedBy(approvedBy);
        document.setApprovedAt(LocalDateTime.now());
        documentRepository.save(document);
        
        // 承認記録を作成
        ApprovalEntity approval = new ApprovalEntity();
        approval.setDocumentId(documentId);
        approval.setApprovalResult("承認");
        approval.setApprovalComment(comment);
        approval.setApprovedBy(approvedBy);
        approval.setApprovedAt(LocalDateTime.now());
        approvalRepository.save(approval);
    }

    /**
     * 申請を却下
     * @param documentId 申請書ID
     * @param approvedBy 承認者ID
     * @param comment コメント
     */
    public void rejectApplication(Integer documentId, Integer approvedBy, String comment) {
        DocumentEntity document = documentRepository.findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException("申請書が見つかりません"));
        
        // 申請書のステータスを更新
        document.setStatus("却下");
        document.setApprovedBy(approvedBy);
        document.setApprovedAt(LocalDateTime.now());
        documentRepository.save(document);
        
        // 承認記録を作成
        ApprovalEntity approval = new ApprovalEntity();
        approval.setDocumentId(documentId);
        approval.setApprovalResult("却下");
        approval.setApprovalComment(comment);
        approval.setApprovedBy(approvedBy);
        approval.setApprovedAt(LocalDateTime.now());
        approvalRepository.save(approval);
    }

    /**
     * 申請書を保存
     * @param document 申請書エンティティ
     * @return 保存された申請書エンティティ
     */
    public DocumentEntity save(DocumentEntity document) {
        if (document.getCreatedAt() == null) {
            document.setCreatedAt(LocalDateTime.now());
        }
        document.setUpdatedAt(LocalDateTime.now());
        return documentRepository.save(document);
    }

    /**
     * 承認待ちの申請書数を取得
     * @return 承認待ちの件数
     */
    public long countPendingApplications() {
        return documentRepository.countByStatus("申請中");
    }

    /**
     * 今日の申請書数を取得
     * @return 今日の申請書数
     */
    public long countTodayApplications() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return documentRepository.countByCreatedAtBetween(startOfDay, endOfDay);
    }
} 