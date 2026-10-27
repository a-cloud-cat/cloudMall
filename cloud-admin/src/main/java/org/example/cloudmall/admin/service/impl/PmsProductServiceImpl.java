package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.PmsProductService;
import org.example.cloudmall.mapper.PmsProductMapper;
import org.example.cloudmall.model.PmsProduct;
import org.example.cloudmall.model.PmsProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsProduct> listProduct(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        example.setOrderByClause("id desc");
        PmsProductExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(example);
    }
}
