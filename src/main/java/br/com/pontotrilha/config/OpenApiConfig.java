package br.com.pontotrilha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API Ponto Trilha")
                        .version("v1")
                        .description("RESTful API developed for the semester conclusion work at Senac Criciúma college.")
                        .termsOfService("https://pontotrilha.com.br/terms")
                        .license(new License().name("Apache 2.0")
                                .url("https://pontotrilha.com.br/licence")));
    }

}
