package SistemeDistribuite.DeviceMicroservice.cofig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.user.queue}")
    private String USER_QUEUE_NAME;
    @Value("${rabbitmq.user.routing-key}")
    private String USER_ROUTING_KEY;

    @Value("${rabbitmq.device.queue}")
    private String DEVICE_QUEUE_NAME;
    @Value("${rabbitmq.device.routing-key}")
    private String DEVICE_ROUTING_KEY;

    @Bean
    public TopicExchange Exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE_NAME, true);
    }
    @Bean Queue deviceQueue() {
        return new Queue(DEVICE_QUEUE_NAME, true);
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with(USER_ROUTING_KEY);
    }
    @Bean
    public Binding deviceBinding(Queue deviceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deviceQueue).to(exchange).with(DEVICE_ROUTING_KEY);
    }
}
