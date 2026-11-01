package org.example.cloudmall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.cloudmall.admin.dto.PmsProductParam;
import org.example.cloudmall.admin.service.PmsProductService;
import org.example.cloudmall.mapper.PmsProductAttributeValueMapper;
import org.example.cloudmall.mapper.PmsProductMapper;
import org.example.cloudmall.mapper.PmsSkuStockMapper;
import org.example.cloudmall.model.PmsProduct;
import org.example.cloudmall.model.PmsProductAttributeValue;
import org.example.cloudmall.model.PmsProductAttributeValueExample;
import org.example.cloudmall.model.PmsProductExample;
import org.example.cloudmall.model.PmsSkuStock;
import org.example.cloudmall.model.PmsSkuStockExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsProductAttributeValueMapper attributeValueMapper;

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

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

    @Override
    public PmsProductParam getProduct(Long id) {
        PmsProduct product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            return null;
        }
        PmsProductParam param = new PmsProductParam();
        BeanUtils.copyProperties(product, param);

        // 查询属性值
        PmsProductAttributeValueExample attrExample = new PmsProductAttributeValueExample();
        attrExample.createCriteria().andProductIdEqualTo(id);
        param.setProductAttributeValueList(attributeValueMapper.selectByExample(attrExample));

        // 查询 SKU
        PmsSkuStockExample skuExample = new PmsSkuStockExample();
        skuExample.createCriteria().andProductIdEqualTo(id);
        param.setSkuStockList(skuStockMapper.selectByExample(skuExample));

        return param;
    }

    @Override
    @Transactional
    public int createProduct(PmsProductParam productParam) {
        int count = productMapper.insertSelective(productParam);
        Long productId = productParam.getId();

        // 保存属性值
        saveAttributeValues(productId, productParam.getProductAttributeValueList());

        // 保存 SKU
        saveSkuStocks(productId, productParam.getSkuStockList());

        return count;
    }

    @Override
    @Transactional
    public int updateProduct(Long id, PmsProductParam productParam) {
        productParam.setId(id);
        int count = productMapper.updateByPrimaryKeySelective(productParam);

        // 先删除原有属性值，再插入新的
        PmsProductAttributeValueExample attrExample = new PmsProductAttributeValueExample();
        attrExample.createCriteria().andProductIdEqualTo(id);
        attributeValueMapper.deleteByExample(attrExample);
        saveAttributeValues(id, productParam.getProductAttributeValueList());

        // 先删除原有 SKU，再插入新的
        PmsSkuStockExample skuExample = new PmsSkuStockExample();
        skuExample.createCriteria().andProductIdEqualTo(id);
        skuStockMapper.deleteByExample(skuExample);
        saveSkuStocks(id, productParam.getSkuStockList());

        return count;
    }

    @Override
    @Transactional
    public int deleteProduct(Long id) {
        // 删除关联的属性值
        PmsProductAttributeValueExample attrExample = new PmsProductAttributeValueExample();
        attrExample.createCriteria().andProductIdEqualTo(id);
        attributeValueMapper.deleteByExample(attrExample);

        // 删除关联的 SKU
        PmsSkuStockExample skuExample = new PmsSkuStockExample();
        skuExample.createCriteria().andProductIdEqualTo(id);
        skuStockMapper.deleteByExample(skuExample);

        return productMapper.deleteByPrimaryKey(id);
    }

    private void saveAttributeValues(Long productId, List<PmsProductAttributeValue> list) {
        if (list == null) {
            return;
        }
        for (PmsProductAttributeValue value : list) {
            value.setProductId(productId);
            attributeValueMapper.insertSelective(value);
        }
    }

    private void saveSkuStocks(Long productId, List<PmsSkuStock> list) {
        if (list == null) {
            return;
        }
        for (PmsSkuStock sku : list) {
            sku.setProductId(productId);
            skuStockMapper.insertSelective(sku);
        }
    }
}
