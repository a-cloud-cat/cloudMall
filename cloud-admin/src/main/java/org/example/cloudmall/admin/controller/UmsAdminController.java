package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.UmsAdminService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.UmsAdmin;
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
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;

    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdmin>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<UmsAdmin> list = adminService.listAdmin(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(adminService.getAdmin(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody UmsAdmin admin) {
        int count = adminService.createAdmin(admin);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.updateAdmin(id, admin);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = adminService.deleteAdmin(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
