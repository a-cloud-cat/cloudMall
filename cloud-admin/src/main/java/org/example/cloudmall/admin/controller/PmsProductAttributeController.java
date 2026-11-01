package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.PmsProductAttributeService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.PmsProductAttribute;
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
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService attributeService;

    @GetMapping("/list/{cid}")
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable("cid") Long cid,
                                                                 @RequestParam(value = "type", required = false) Integer type,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<PmsProductAttribute> list = attributeService.listByCategory(cid, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/{id}")
    public CommonResult<PmsProductAttribute> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(attributeService.getItem(id));
    }

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody PmsProductAttribute attribute) {
        int count = attributeService.create(attribute);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id,
                                        @RequestBody PmsProductAttribute attribute) {
        int count = attributeService.update(id, attribute);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = attributeService.delete(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
