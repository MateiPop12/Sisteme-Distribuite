package SistemeDistribuite.Monitoring.CommunicationMicroservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String EXCHANGE;

    @Value("${rabbitmq.sim.queue}")
    private String SIM_QUEUE;
    @Value("${rabbitmq.device.queue}")
    private String DEVICE_QUEUE;

    @Value("${rabbitmq.sim.routing-key}")
    private String SIM_ROUTING_KEY;
    @Value("${rabbitmq.device.routing-key}")
    private String DEVICE_ROUTING_KEY;

    @Bean
    public TopicExchange exchange(){return new TopicExchange(EXCHANGE);}

    @Bean
    public Queue simQueue(){return new Queue(SIM_QUEUE);}
    @Bean
    public Queue deviceQueue(){return new Queue(DEVICE_QUEUE);}

    @Bean
    public Binding simBinding(Queue simQueue, TopicExchange exchange) {
        return BindingBuilder.bind(simQueue).to(exchange).with(SIM_ROUTING_KEY);
    }
    @Bean
    public Binding deviceBinding(Queue deviceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deviceQueue).to(exchange).with(DEVICE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public MessageConverter simpleMessageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public RabbitTemplate simpleRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(simpleMessageConverter());
        return rabbitTemplate;
    }
}
