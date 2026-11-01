package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsSkuStockService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsSkuStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {

    @Autowired
    private PmsSkuStockService skuStockService;

    @GetMapping("/{productId}")
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable("productId") Long productId) {
        return CommonResult.success(skuStockService.listByProduct(productId));
    }

    @PostMapping("/update/{productId}")
    public CommonResult<Integer> update(@PathVariable("productId") Long productId,
                                        @RequestBody List<PmsSkuStock> skuStockList) {
        int count = skuStockService.update(productId, skuStockList);
        return CommonResult.success(count);
    }
}
