package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsProductAttributeCategoryService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsProductAttributeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService categoryService;

    @GetMapping("/list")
    public CommonResult<List<PmsProductAttributeCategory>> getList() {
        return CommonResult.success(categoryService.listAll());
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody PmsProductAttributeCategory category) {
        int count = categoryService.create(category);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id,
                                        @RequestBody PmsProductAttributeCategory category) {
        int count = categoryService.update(id, category);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = categoryService.delete(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
