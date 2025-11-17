package com.start.ecom.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration for Swagger/SpringDoc documentation
 * Provides detailed API metadata and description
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Backend API")
                        .version("1.0.0")
                        .description("RESTful API for managing e-commerce products. Supports CRUD operations, " +
                                "product search, filtering, pagination, and image management. " +
                                "Built with Spring Boot 3.4.2, featuring Redis caching for performance optimization.")
                        .contact(new Contact()
                                .name("Ash_mo")
                                .email("mohamed_shaaban_ism@outlook.com")
                                .url("https://ecommerce.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
