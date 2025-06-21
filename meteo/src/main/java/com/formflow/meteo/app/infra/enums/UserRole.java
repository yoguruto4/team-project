package com.formflow.meteo.app.infra.enums;

/**
 * ユーザーロール定義
 */
public enum UserRole {
    ADMIN("管理者", 1),
    EMPLOYEE("従業員", 0);

    private final String displayName;
    private final int value;

    UserRole(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }

    /**
     * adminAuthの値からUserRoleを取得
     * @param adminAuth データベースのadminAuthの値
     * @return UserRole
     */
    public static UserRole fromAdminAuth(int adminAuth) {
        if (adminAuth == 1) {
            return ADMIN;
        } else {
            return EMPLOYEE;
        }
    }

    /**
     * Spring SecurityのROLE_プレフィックス付きロール名を取得
     * @return ロール名
     */
    public String getRoleName() {
        return "ROLE_" + this.name();
    }
} 