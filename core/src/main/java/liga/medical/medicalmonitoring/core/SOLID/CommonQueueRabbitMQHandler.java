package liga.medical.medicalmonitoring.core.SOLID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import liga.medical.medicalmonitoring.core.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonQueueRabbitMQHandler {
    private final AmqpTemplate template;

    @Autowired
    public CommonQueueRabbitMQHandler(AmqpTemplate template) {
        this.template = template;
    }

    public void handle(Message message) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(message);
            template.convertAndSend("common_monitoring", message.getStatus().toString(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
