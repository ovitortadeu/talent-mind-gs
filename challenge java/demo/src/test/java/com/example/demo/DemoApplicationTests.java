package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.challenge_java.DemoApplication;

/**
 * FIX: Apontando o @SpringBootTest para a classe de configuração
 * principal (DemoApplication), já que os pacotes são diferentes.
 */
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}