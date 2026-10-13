package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsBrandService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌管理接口
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService brandService;

    @GetMapping("/listAll")
    public CommonResult<List<PmsBrand>> getList() {
        return CommonResult.success(brandService.listAllBrand());
    }

    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsBrand> brandList = brandService.listBrand(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @GetMapping("/{id}")
    public CommonResult<PmsBrand> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(brandService.getBrand(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody PmsBrand brand) {
        int count = brandService.createBrand(brand);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody PmsBrand brand) {
        int count = brandService.updateBrand(id, brand);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = brandService.deleteBrand(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
