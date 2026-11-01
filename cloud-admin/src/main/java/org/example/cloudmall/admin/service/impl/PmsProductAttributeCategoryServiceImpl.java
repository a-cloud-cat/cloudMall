package org.example.cloudmall.admin.service.impl;

import org.example.cloudmall.admin.service.PmsProductAttributeCategoryService;
import org.example.cloudmall.mapper.PmsProductAttributeCategoryMapper;
import org.example.cloudmall.model.PmsProductAttributeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper categoryMapper;

    @Override
    public List<PmsProductAttributeCategory> listAll() {
        return categoryMapper.selectByExample(null);
    }

    @Override
    public int create(PmsProductAttributeCategory category) {
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int update(Long id, PmsProductAttributeCategory category) {
        category.setId(id);
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }
}
