package com.formflow.meteo.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.formflow.meteo.app.infra.entity.EmployeeEntity;
import com.formflow.meteo.app.infra.enums.UserRole;
import com.formflow.meteo.app.infra.repository.EmployeeRepository;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 現在ログインしているユーザーのEmployeeEntityを取得
     * @return EmployeeEntity
     */
    public EmployeeEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();
        return employeeRepository.findByEmail(email)
            .orElse(null);
    }

    /**
     * 現在ログインしているユーザーが管理者かどうかチェック
     * @return 管理者の場合true
     */
    public boolean isCurrentUserAdmin() {
        EmployeeEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        UserRole userRole = UserRole.fromAdminAuth(currentUser.getAdminAuth());
        return userRole == UserRole.ADMIN;
    }

    /**
     * 現在ログインしているユーザーが従業員かどうかチェック
     * @return 従業員の場合true
     */
    public boolean isCurrentUserEmployee() {
        EmployeeEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        UserRole userRole = UserRole.fromAdminAuth(currentUser.getAdminAuth());
        return userRole == UserRole.EMPLOYEE;
    }

    /**
     * 現在ログインしているユーザーのロールを取得
     * @return UserRole
     */
    public UserRole getCurrentUserRole() {
        EmployeeEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }

        return UserRole.fromAdminAuth(currentUser.getAdminAuth());
    }

    /**
     * 指定したユーザーIDのユーザーが現在ログインしているユーザーかチェック
     * @param employeeId ユーザーID
     * @return 同じユーザーの場合true
     */
    public boolean isCurrentUser(Integer employeeId) {
        EmployeeEntity currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        return currentUser.getIdEmployee().equals(employeeId);
    }

    /**
     * 現在ログインしているユーザーが管理者または本人かチェック
     * @param employeeId ユーザーID
     * @return 管理者または本人の場合true
     */
    public boolean isCurrentUserAdminOrSelf(Integer employeeId) {
        return isCurrentUserAdmin() || isCurrentUser(employeeId);
    }
} 