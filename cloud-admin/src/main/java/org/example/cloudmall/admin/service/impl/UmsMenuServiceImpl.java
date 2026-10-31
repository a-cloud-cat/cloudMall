package org.example.cloudmall.admin.service.impl;

import org.example.cloudmall.admin.service.UmsMenuService;
import org.example.cloudmall.mapper.UmsMenuMapper;
import org.example.cloudmall.model.UmsMenu;
import org.example.cloudmall.model.UmsMenuExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public List<UmsMenu> listMenu(Long parentId) {
        UmsMenuExample example = new UmsMenuExample();
        example.setOrderByClause("sort asc");
        if (parentId != null) {
            example.createCriteria().andParentIdEqualTo(parentId);
        }
        return menuMapper.selectByExample(example);
    }

    @Override
    public UmsMenu getMenu(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int createMenu(UmsMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        menu.setLevel(menu.getParentId() == 0 ? 0 : 1);
        menu.setCreateTime(new Date());
        return menuMapper.insertSelective(menu);
    }

    @Override
    public int updateMenu(Long id, UmsMenu menu) {
        menu.setId(id);
        if (menu.getParentId() != null) {
            menu.setLevel(menu.getParentId() == 0 ? 0 : 1);
        }
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int deleteMenu(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }
}
