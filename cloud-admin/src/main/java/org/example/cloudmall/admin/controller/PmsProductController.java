package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.dto.PmsProductParam;
import org.example.cloudmall.admin.service.PmsProductService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public CommonResult<PmsProductParam> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(productService.getProduct(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody PmsProductParam productParam) {
        int count = productService.createProduct(productParam);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody PmsProductParam productParam) {
        int count = productService.updateProduct(id, productParam);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = productService.deleteProduct(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
