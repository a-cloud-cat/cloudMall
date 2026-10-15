package org.example.cloudmall.auth.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.example.cloudmall.mapper.UmsAdminMapper;
import org.example.cloudmall.model.UmsAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * AuthService 登录分支单测：桩掉 Mapper，不连库、不启容器
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UmsAdminMapper umsAdminMapper;

    @InjectMocks
    private AuthService authService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private UmsAdmin activeAdmin() {
        UmsAdmin admin = new UmsAdmin();
        admin.setId(1L);
        admin.setUsername("admin1");
        admin.setPassword(encoder.encode("123456"));
        admin.setStatus(1);
        return admin;
    }

    @Test
    void loginSuccessReturnsAdmin() {
        UmsAdmin admin = activeAdmin();
        when(umsAdminMapper.selectByExample(any())).thenReturn(List.of(admin));

        UmsAdmin result = authService.login("admin1", "123456");

        assertNotNull(result);
        assertSame(admin, result);
    }

    @Test
    void wrongPasswordReturnsNull() {
        when(umsAdminMapper.selectByExample(any())).thenReturn(List.of(activeAdmin()));

        assertNull(authService.login("admin1", "wrong"));
    }

    @Test
    void disabledStatusReturnsNull() {
        UmsAdmin admin = activeAdmin();
        admin.setStatus(0);
        when(umsAdminMapper.selectByExample(any())).thenReturn(List.of(admin));

        assertNull(authService.login("admin1", "123456"));
    }

    @Test
    void unknownUserReturnsNull() {
        when(umsAdminMapper.selectByExample(any())).thenReturn(Collections.emptyList());

        assertNull(authService.login("nobody", "123456"));
    }

    @Test
    void nullArgumentReturnsNull() {
        assertNull(authService.login(null, "123456"));
        assertNull(authService.login("admin1", null));
    }
}
