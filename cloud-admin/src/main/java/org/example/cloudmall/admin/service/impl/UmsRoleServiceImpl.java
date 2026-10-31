package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.UmsRoleService;
import org.example.cloudmall.mapper.UmsRoleMapper;
import org.example.cloudmall.model.UmsRole;
import org.example.cloudmall.model.UmsRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Override
    public List<UmsRole> listRole(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        example.setOrderByClause("sort asc, id desc");
        UmsRoleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public UmsRole getRole(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int createRole(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(role.getStatus() == null ? 1 : role.getStatus());
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRole(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(Long id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
