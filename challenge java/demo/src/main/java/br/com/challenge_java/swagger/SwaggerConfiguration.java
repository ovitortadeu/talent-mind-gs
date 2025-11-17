package br.com.challenge_java.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI configurarSwagger() {
        
        final String securitySchemeName = "bearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        Info info = new Info()
                .title("API TalentMind - GS Java Advanced")
                .description("API RESTful para a Global Solution 2025, focada no tema 'O Futuro do Trabalho'.")
                .version("v1.0.0")
                .license(new License().url("https://www.fiap.com.br")
                                      .name("FIAP Global Solution"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement) 
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, securityScheme));
    }
}