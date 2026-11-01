package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsSkuStock;

import java.util.List;

public interface PmsSkuStockService {

    List<PmsSkuStock> listByProduct(Long productId);

    int update(Long productId, List<PmsSkuStock> skuStockList);
}
