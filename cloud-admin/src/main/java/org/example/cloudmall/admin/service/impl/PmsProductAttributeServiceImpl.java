package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.PmsProductAttributeService;
import org.example.cloudmall.mapper.PmsProductAttributeMapper;
import org.example.cloudmall.model.PmsProductAttribute;
import org.example.cloudmall.model.PmsProductAttributeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper attributeMapper;

    @Override
    public List<PmsProductAttribute> listByCategory(Long cid, Integer type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort asc, id desc");
        PmsProductAttributeExample.Criteria criteria = example.createCriteria();
        if (cid != null) {
            criteria.andProductAttributeCategoryIdEqualTo(cid);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        return attributeMapper.selectByExample(example);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return attributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int create(PmsProductAttribute attribute) {
        return attributeMapper.insertSelective(attribute);
    }

    @Override
    public int update(Long id, PmsProductAttribute attribute) {
        attribute.setId(id);
        return attributeMapper.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public int delete(Long id) {
        return attributeMapper.deleteByPrimaryKey(id);
    }
}
