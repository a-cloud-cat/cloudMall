package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsProductService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<PmsProduct> productList = productService.listProduct(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }
}
