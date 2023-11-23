package br.com.pontotrilha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API Pontotrilha")
						.version("v1")
						.description("API for the Pontotrilha application")
						.contact(new Contact()
								.name("Pontotrilha Team").email("contato@pontotrilha.com"))
						.termsOfService("https://www.pontotrilha.com.br/termo")
						.license(
								new License()
										.name("Apache 2.0")
										.url("https://www.pontotrilha.com.br/licensa")));
	}
}
