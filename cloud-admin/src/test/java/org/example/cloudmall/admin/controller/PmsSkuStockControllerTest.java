package org.example.cloudmall.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SKU 库存接口集成测试，真实连接 MySQL，事务自动回滚不污染数据
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PmsSkuStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listByProductReturnsArray() throws Exception {
        mockMvc.perform(get("/sku/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void updateReplacesSkuList() throws Exception {
        String body = "[{\"skuCode\":\"TEST-SKU-DEL\",\"price\":1.00,\"stock\":1,\"lowStock\":0,\"spData\":\"{\\\"颜色\\\":\\\"测试\\\"}\"}]";
        mockMvc.perform(post("/sku/update/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/sku/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].skuCode").value("TEST-SKU-DEL"));
    }
}
