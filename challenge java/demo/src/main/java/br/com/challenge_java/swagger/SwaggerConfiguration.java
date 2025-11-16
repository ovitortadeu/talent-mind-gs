package br.com.challenge_java.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	OpenAPI configurarSwagger() {
		
		return new OpenAPI().info(new Info()
											.title("Projeto de Gerenciamento de motos")
											.description("Este projeto oferece uma implementação que possibilita "
													+ "a gestão de motos nos pátios especificos da Mottu ")
											.summary("Sumário: Este projeto oferece uma implementação que possibilita"
													+ " a gestão dos veículos gerenciados pela Mottu")
											.version("v1.0.0")
											.license(new License().url("www.mottu.com.br")
																  .name("Licença - Projeto de Gestão de veiculos - v1.0.0"))
											.termsOfService("Termos de Serviço"));
		
	}

}
