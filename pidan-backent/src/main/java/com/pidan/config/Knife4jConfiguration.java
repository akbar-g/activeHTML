package com.pidan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/** Swagger配置依赖
 * @author 黄大头
 * @date 2023年03月26日 22:50
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "example")
    public Docket example() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("好友池")
                        .description("接口文档")
                        .termsOfServiceUrl("http://127.0.0.1:6666")
                        .contact(new Contact("pidan", "http:localhost:8080", "133@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("演示实例接口")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.pidan.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}