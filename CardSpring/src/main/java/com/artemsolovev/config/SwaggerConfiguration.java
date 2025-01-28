package com.artemsolovev.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Card API")
                                .description("User cards with categories")
                                .version("1.0.0")
                                .contact(new io.swagger.v3.oas.models.info.Contact()
                                        .name("Solovyev Artem")
                                        .email("my@gmail.com")
                                )
                );
    }
}
