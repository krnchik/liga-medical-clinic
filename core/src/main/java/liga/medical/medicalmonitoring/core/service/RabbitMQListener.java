package liga.medical.medicalmonitoring.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.medicalmonitoring.core.model.Message;
import liga.medical.medicalmonitoring.core.model.Status;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    private final RabbitTemplate template;

    @Autowired
    public RabbitMQListener(RabbitTemplate template) {
        this.template = template;
    }

    @RabbitListener(queues = "common_monitoring")
    public void exchange(String str) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(str, Message.class);
        if (message.getStatus().equals(Status.ERROR))
            template.convertAndSend("common_monitoring", Status.ERROR.toString(), message);
        if (message.getStatus().equals(Status.DAILY))
            template.convertAndSend("common_monitoring", Status.DAILY.toString(), message);
        if (message.getStatus().equals(Status.ALERT))
            template.convertAndSend("common_monitoring", Status.ALERT.toString(), message);
    }
}
