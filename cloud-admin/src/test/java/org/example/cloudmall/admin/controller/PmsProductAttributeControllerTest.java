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
 * 商品属性接口集成测试，真实连接 MySQL，事务自动回滚不污染数据
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PmsProductAttributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listByCategoryReturnsPage() throws Exception {
        mockMvc.perform(get("/productAttribute/list/1")
                        .param("pageNum", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.pageNum").value(1))
                .andExpect(jsonPath("$.data.list").isArray());
    }

    @Test
    void createGetDeleteRoundTrip() throws Exception {
        String body = "{\"productAttributeCategoryId\":1,\"name\":\"测试属性\",\"selectType\":1,\"inputType\":1,\"inputList\":\"A,B\",\"sort\":0,\"type\":0}";
        mockMvc.perform(post("/productAttribute/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String listJson = mockMvc.perform(get("/productAttribute/list/1")
                        .param("pageNum", "1")
                        .param("pageSize", "50"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(listJson, "$.data.list[?(@.name=='测试属性')].id").toString()
                .replaceAll("[\\[\\]]", "");

        mockMvc.perform(get("/productAttribute/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("测试属性"));

        mockMvc.perform(get("/productAttribute/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
