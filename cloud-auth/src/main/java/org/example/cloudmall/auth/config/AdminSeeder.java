package org.example.cloudmall.auth.config;

import java.util.Date;
import java.util.List;
import org.example.cloudmall.mapper.UmsAdminMapper;
import org.example.cloudmall.model.UmsAdmin;
import org.example.cloudmall.model.UmsAdminExample;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/** 启动时播种测试账号 admin1/123，已存在则跳过 */
@Component
public class AdminSeeder implements ApplicationRunner {

    private final UmsAdminMapper umsAdminMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminSeeder(UmsAdminMapper umsAdminMapper) {
        this.umsAdminMapper = umsAdminMapper;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (exists("admin1")) {
            return;
        }
        UmsAdmin admin = new UmsAdmin();
        admin.setUsername("admin1");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setNickName("超级管理员");
        admin.setStatus(1);
        admin.setCreateTime(new Date());
        umsAdminMapper.insertSelective(admin);
    }

    private boolean exists(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> list = umsAdminMapper.selectByExample(example);
        return !list.isEmpty();
    }
}
