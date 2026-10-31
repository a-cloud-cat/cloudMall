package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.UmsMenuService;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.UmsMenu;
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
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @GetMapping("/list")
    public CommonResult<List<UmsMenu>> getList(@RequestParam(value = "parentId", required = false) Long parentId) {
        return CommonResult.success(menuService.listMenu(parentId));
    }

    @GetMapping("/{id}")
    public CommonResult<UmsMenu> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(menuService.getMenu(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody UmsMenu menu) {
        int count = menuService.createMenu(menu);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody UmsMenu menu) {
        int count = menuService.updateMenu(id, menu);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = menuService.deleteMenu(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
