package org.example.cloudmall.admin.service.impl;

import org.example.cloudmall.admin.service.PmsProductCategoryService;
import org.example.cloudmall.mapper.PmsProductCategoryMapper;
import org.example.cloudmall.model.PmsProductCategory;
import org.example.cloudmall.model.PmsProductCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper categoryMapper;

    @Override
    public List<PmsProductCategory> listCategory(Long parentId) {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort asc");
        if (parentId != null) {
            example.createCriteria().andParentIdEqualTo(parentId);
        }
        return categoryMapper.selectByExample(example);
    }

    @Override
    public PmsProductCategory getCategory(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int createCategory(PmsProductCategory category) {
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
        category.setLevel(category.getParentId() == 0 ? 0 : 1);
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int updateCategory(Long id, PmsProductCategory category) {
        category.setId(id);
        if (category.getParentId() != null) {
            category.setLevel(category.getParentId() == 0 ? 0 : 1);
        }
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int deleteCategory(Long id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }
}
