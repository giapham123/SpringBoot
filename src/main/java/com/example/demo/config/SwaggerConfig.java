package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API Documentation")
                        .version("1.0")
                        .description("API documentation with Basic Authentication"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth")) // Link security to endpoints
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic"))); // Define Basic Authentication
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/basic/**") // Only include public APIs
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("private")
                .pathsToExclude("/api/authenticate","/api/basic1/**","/api/secure-endpoint","/api/basic/test11","/api/basic/test","/api/basic/test1") // Exclude private APIs
                .build();
    }
}


