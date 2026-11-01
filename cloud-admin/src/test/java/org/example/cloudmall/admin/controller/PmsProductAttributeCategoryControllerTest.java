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
 * 商品属性分类接口集成测试，真实连接 MySQL，事务自动回滚不污染数据
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PmsProductAttributeCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listReturnsArray() throws Exception {
        mockMvc.perform(get("/productAttribute/category/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void createGetDeleteRoundTrip() throws Exception {
        String body = "{\"name\":\"测试分类\"}";
        mockMvc.perform(post("/productAttribute/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String listJson = mockMvc.perform(get("/productAttribute/category/list"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(listJson, "$.data[?(@.name=='测试分类')].id").toString()
                .replaceAll("[\\[\\]]", "");

        mockMvc.perform(get("/productAttribute/category/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
