package com.cpay.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
//@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
//@OpenAPIDefinition(security = {SecurityRequirement(name = "BearerAuthentication") })
public class SwaggerOpenApiConfig {
	@Bean
	public OpenAPI springOpenAPI() {
		// final String securitySchemeName = "bearerAuth";
		final String securitySchemeName = "BearerAuthentication";
		return new OpenAPI()
				// defining security scheme
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				// new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				// setting global security
				.security(List.of(new SecurityRequirement().addList(securitySchemeName)))
				.info(new Info().title("Credit Card API").description("Credit Card Management API")
						.version("v1.0.0").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("This API provides endpoints for managing "
						+ "credit card applications, user authentication, user details, and transaction processing. It uses JWT-based authentication for secure access and operations.")
						.url("https://springboot.wiki.github.org/docs"));

	}

}