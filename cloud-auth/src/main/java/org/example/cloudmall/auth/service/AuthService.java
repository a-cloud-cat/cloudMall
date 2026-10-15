package org.example.cloudmall.auth.service;

import java.util.List;
import org.example.cloudmall.mapper.UmsAdminMapper;
import org.example.cloudmall.model.UmsAdmin;
import org.example.cloudmall.model.UmsAdminExample;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 后台登录：按用户名查 ums_admin，再用 BCrypt 校验密码
 */
@Service
public class AuthService {

    private final UmsAdminMapper umsAdminMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UmsAdminMapper umsAdminMapper) {
        this.umsAdminMapper = umsAdminMapper;
    }

    public UmsAdmin login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        UmsAdmin admin = getByUsername(username);
        // status 为空或不为 1 都视为不可登录
        if (admin == null || admin.getStatus() == null || admin.getStatus() != 1) {
            return null;
        }
        return passwordEncoder.matches(password, admin.getPassword()) ? admin : null;
    }

    private UmsAdmin getByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> list = umsAdminMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }
}
