package org.example.cloudmall.admin.controller;

import org.example.cloudmall.admin.service.OmsOrderService;
import org.example.cloudmall.common.result.CommonPage;
import org.example.cloudmall.common.result.CommonResult;
import org.example.cloudmall.model.OmsOrder;
import org.example.cloudmall.model.OmsOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired
    private OmsOrderService orderService;

    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrder>> getList(@RequestParam(value = "status", required = false) Integer status,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<OmsOrder> list = orderService.listOrder(status, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/{id}")
    public CommonResult<Map<String, Object>> getItem(@PathVariable("id") Long id) {
        OmsOrder order = orderService.getOrder(id);
        List<OmsOrderItem> itemList = orderService.listOrderItem(id);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("itemList", itemList);
        return CommonResult.success(result);
    }

    @PostMapping("/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id, @RequestBody OmsOrder order) {
        int count = orderService.updateOrder(id, order);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        int count = orderService.deleteOrder(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
}
