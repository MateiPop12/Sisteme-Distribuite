package SistemeDistribuite.Monitoring.CommunicationMicroservice.config;

import org.springframework.beans.factory.annotation.Value;

public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.sim.queue}")
    private String SIME_QUEUE;

    @Value("${rabbitmq.sim.routing-key}")
    private String SIME_ROUTING_KEY;
}
