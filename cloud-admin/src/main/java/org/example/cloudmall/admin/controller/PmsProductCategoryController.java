package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsProductCategoryService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsProductCategory;
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
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService categoryService;

    @GetMapping("/list")
    public CommonResult<List<PmsProductCategory>> getList(@RequestParam(value = "parentId", required = false) Long parentId) {
        return CommonResult.success(categoryService.listCategory(parentId));
    }

    @GetMapping("/{id}")
    public CommonResult<PmsProductCategory> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(categoryService.getCategory(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody PmsProductCategory category) {
        int count = categoryService.createCategory(category);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody PmsProductCategory category) {
        int count = categoryService.updateCategory(id, category);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = categoryService.deleteCategory(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
