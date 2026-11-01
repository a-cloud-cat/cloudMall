package org.example.cloudmall.admin.service.impl;

import org.example.cloudmall.admin.service.PmsSkuStockService;
import org.example.cloudmall.mapper.PmsSkuStockMapper;
import org.example.cloudmall.model.PmsSkuStock;
import org.example.cloudmall.model.PmsSkuStockExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    @Override
    public List<PmsSkuStock> listByProduct(Long productId) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        example.createCriteria().andProductIdEqualTo(productId);
        return skuStockMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public int update(Long productId, List<PmsSkuStock> skuStockList) {
        // 先删除该商品原有 SKU
        PmsSkuStockExample example = new PmsSkuStockExample();
        example.createCriteria().andProductIdEqualTo(productId);
        skuStockMapper.deleteByExample(example);

        // 再插入新的 SKU 列表
        int count = 0;
        if (skuStockList != null) {
            for (PmsSkuStock sku : skuStockList) {
                sku.setProductId(productId);
                count += skuStockMapper.insertSelective(sku);
            }
        }
        return count;
    }
}
