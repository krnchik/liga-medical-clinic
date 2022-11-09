package liga.medical.medicalmonitoring.core.config;

import liga.medical.common.dto.Type;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(host);
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange("common_monitoring");
        return rabbitTemplate;
    }

    @Bean
    @Qualifier("daily_queue")
    public Queue dailyQueue() {
        return new Queue("daily_queue");
    }

    @Bean
    @Qualifier("alert_queue")
    public Queue alertQueue() {
        return new Queue("alert_queue");
    }

    @Bean
    @Qualifier("error_queue")
    public Queue errorQueue() {
        return new Queue("error_queue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("common_monitoring");
    }

    @Bean
    @Qualifier("daily")
    public Binding dailyBinding() {
        return BindingBuilder.bind(dailyQueue()).to(directExchange()).with(Type.DAILY.toString());
    }

    @Bean
    @Qualifier("alert")
    public Binding alertBinding() {
        return BindingBuilder.bind(alertQueue()).to(directExchange()).with(Type.ALERT.toString());
    }

    @Bean
    @Qualifier("error")
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with(Type.ERROR.toString());
    }
}
