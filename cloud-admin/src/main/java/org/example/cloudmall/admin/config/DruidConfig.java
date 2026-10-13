package org.example.cloudmall.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid 连接池与监控页配置
 */
@Configuration
public class DruidConfig {

    /**
     * application.yml 里 spring.datasource.druid 前缀的属性绑定到 DruidDataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 监控页 http://localhost:8082/druid
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> registration =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registration.addInitParameter("loginUsername", "admin");
        registration.addInitParameter("loginPassword", "admin");
        return registration;
    }
}
