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
 * 商品接口集成测试，验证嵌套属性值与 SKU 的保存和查询，真实连接 MySQL，事务自动回滚不污染数据
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PmsProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProductWithAttrAndSkuThenDetailReturnsNested() throws Exception {
        String body = "{"
                + "\"name\":\"测试商品-含规格\","
                + "\"brandId\":6,\"brandName\":\"小米\","
                + "\"price\":100.00,\"pic\":\"x.jpg\",\"productSn\":\"TEST-NESTED\",\"publishStatus\":1,\"sale\":0,"
                + "\"productAttributeValueList\":[{\"productAttributeId\":1,\"value\":\"测试色\"}],"
                + "\"skuStockList\":[{\"skuCode\":\"NESTED-SKU-1\",\"price\":100.00,\"stock\":10,\"lowStock\":1,\"spData\":\"{\\\"颜色\\\":\\\"测试色\\\"}\"}]"
                + "}";

        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String listJson = mockMvc.perform(get("/product/list")
                        .param("keyword", "测试商品-含规格")
                        .param("pageNum", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(listJson, "$.data.list[0].id").toString();

        mockMvc.perform(get("/product/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("测试商品-含规格"))
                .andExpect(jsonPath("$.data.productAttributeValueList").isArray())
                .andExpect(jsonPath("$.data.productAttributeValueList[0].value").value("测试色"))
                .andExpect(jsonPath("$.data.skuStockList").isArray())
                .andExpect(jsonPath("$.data.skuStockList[0].skuCode").value("NESTED-SKU-1"));

        mockMvc.perform(get("/product/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
