package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.UmsRoleService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.UmsRole;
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
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @GetMapping("/list")
    public CommonResult<CommonPage<UmsRole>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<UmsRole> list = roleService.listRole(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/{id}")
    public CommonResult<UmsRole> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(roleService.getRole(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody UmsRole role) {
        int count = roleService.createRole(role);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody UmsRole role) {
        int count = roleService.updateRole(id, role);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = roleService.deleteRole(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
