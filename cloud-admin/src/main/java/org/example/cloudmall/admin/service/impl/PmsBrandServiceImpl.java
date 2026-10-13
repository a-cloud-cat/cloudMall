package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.service.PmsBrandService;
import org.example.cloudmall.mapper.PmsBrandMapper;
import org.example.cloudmall.model.PmsBrand;
import org.example.cloudmall.model.PmsBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌业务实现
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        if (!StringUtils.hasText(brand.getFirstLetter()) && StringUtils.hasText(brand.getName())) {
            brand.setFirstLetter(brand.getName().substring(0, 1));
        }
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example = new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return brandMapper.selectByExample(example);
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
