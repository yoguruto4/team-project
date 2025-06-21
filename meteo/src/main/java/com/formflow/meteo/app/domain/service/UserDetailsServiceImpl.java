package com.formflow.meteo.app.domain.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formflow.meteo.app.infra.entity.EmployeeEntity;
import com.formflow.meteo.app.infra.enums.UserRole;
import com.formflow.meteo.app.infra.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmployeeEntity employee = employeeRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));

        return new User(
            employee.getEmail(),
            employee.getPassword(),
            getAuthorities(employee.getAdminAuth())
        );
    }

    /**
     * ユーザーの権限を取得
     * @param adminAuth 管理者権限フラグ
     * @return 権限リスト
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Integer adminAuth) {
        UserRole userRole = UserRole.fromAdminAuth(adminAuth);
        return List.of(new SimpleGrantedAuthority(userRole.getRoleName()));
    }
} 