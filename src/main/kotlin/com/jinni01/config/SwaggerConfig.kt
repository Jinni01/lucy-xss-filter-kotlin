package com.jinni01.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openApiDefinition(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("lucy-xss-filter-kotlin")
            .addOpenApiCustomizer {
                it.info(
                    Info()
                        .title("xss filter API test docs")
                        .description("필터 API 테스트 문서")
                        .version("v1.0.0")
                )
            }
            .pathsToMatch("/**")
            .build()
    }
}