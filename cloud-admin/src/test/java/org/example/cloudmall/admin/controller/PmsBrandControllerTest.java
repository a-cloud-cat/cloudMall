package org.example.cloudmall.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 品牌接口集成测试，真实连接 MySQL
 */
@SpringBootTest
@AutoConfigureMockMvc
class PmsBrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listPageReturnsPageStructure() throws Exception {
        mockMvc.perform(get("/brand/list")
                        .param("pageNum", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.pageNum").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(5))
                .andExpect(jsonPath("$.data.list").isArray());
    }

    @Test
    void createGetDeleteRoundTrip() throws Exception {
        String body = "{\"name\":\"测试品牌X\",\"firstLetter\":\"C\",\"sort\":0}";
        String content = mockMvc.perform(post("/brand/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn().getResponse().getContentAsString();

        // create 返回的 data 固定为 1，取不到自增 id，改用名称模糊查到它
        String listJson = mockMvc.perform(get("/brand/list")
                        .param("keyword", "测试品牌X")
                        .param("pageNum", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(listJson, "$.data.list[0].id").toString();
        mockMvc.perform(get("/brand/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("测试品牌X"));

        mockMvc.perform(get("/brand/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/brand/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(org.hamcrest.Matchers.nullValue()));
    }
}
