package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.UmsAdminService;
import org.example.cloudmall.mapper.UmsAdminMapper;
import org.example.cloudmall.model.UmsAdmin;
import org.example.cloudmall.model.UmsAdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UmsAdmin> listAdmin(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        example.setOrderByClause("id desc");
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
        }
        List<UmsAdmin> list = adminMapper.selectByExample(example);
        list.forEach(a -> a.setPassword(null));
        return list;
    }

    @Override
    public UmsAdmin getAdmin(Long id) {
        UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
        if (admin != null) {
            admin.setPassword(null);
        }
        return admin;
    }

    @Override
    public int createAdmin(UmsAdmin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setCreateTime(new Date());
        admin.setStatus(admin.getStatus() == null ? 1 : admin.getStatus());
        return adminMapper.insertSelective(admin);
    }

    @Override
    public int updateAdmin(Long id, UmsAdmin admin) {
        admin.setId(id);
        if (StringUtils.hasText(admin.getPassword())) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        } else {
            admin.setPassword(null);
        }
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int deleteAdmin(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }
}
