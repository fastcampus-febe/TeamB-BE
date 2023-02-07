package com.teamb.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .groupName("TeamB-BE Travel Project")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.teamb.travel"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Travel API Documentation")
                .description("날씨 기반 관광지 추천 서비스 API 입니다.")
                .license("김예은/권동현/전홍석")
                .licenseUrl("https://github.com/fastcampus-febe/TeamB-BE")
                .version("1.0")
                .build();
    }

}
