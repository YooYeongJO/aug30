package com.jose1593.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration // 클래스이긴 하지만 설정 파일
public class SwaggerConfig { // Swagger는 문서화 할 때 사용한다
	
	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jose1593.web")) 
				// 내 패키지 안에 있는 파일 싹다 불러
				.paths(PathSelectors.any())
				.build();
				
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("중앙학원")
				.version("0.0.1")
				.description("여긴 설명...")
				.build();
	}

}
