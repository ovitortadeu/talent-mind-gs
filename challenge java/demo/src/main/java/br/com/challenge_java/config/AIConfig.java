package br.com.challenge_java.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    /**
     * Cria o Bean do ChatClient, que é a interface fluente 
     * para interagir com o modelo de IA (Ollama).
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}