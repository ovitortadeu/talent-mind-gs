package br.com.challenge_java.config.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Define o nome da fila onde as novas vagas serão publicadas.
     * A nomenclatura "q.talentmind.vaga-nova" é uma boa prática.
     */
    public static final String QUEUE_VAGA_NOVA = "q.talentmind.vaga-nova";
    
    /**
     * Define o nome da exchange (roteador) principal.
     * "dx.talentmind" (Direct Exchange).
     */
    public static final String EXCHANGE_TALENTMIND = "dx.talentmind";

    /**
     * Define a routing key (chave de roteamento) que liga a exchange à fila.
     * "rk.vaga-nova".
     */
    public static final String ROUTING_KEY_VAGA_NOVA = "rk.vaga-nova";

    @Bean
    Queue vagaNovaQueue() {
        // durable = true (a fila sobrevive a reinícios do RabbitMQ)
        return new Queue(QUEUE_VAGA_NOVA, true);
    }

    @Bean
    DirectExchange talentMindExchange() {
        return new DirectExchange(EXCHANGE_TALENTMIND);
    }

    @Bean
    Binding bindingVagaNova(Queue vagaNovaQueue, DirectExchange talentMindExchange) {
        // Liga a fila 'vagaNovaQueue' à exchange 'talentMindExchange'
        // usando a chave 'ROUTING_KEY_VAGA_NOVA'
        return BindingBuilder.bind(vagaNovaQueue)
                             .to(talentMindExchange)
                             .with(ROUTING_KEY_VAGA_NOVA);
    }
}