package liga.medical.medicalmonitoring.core.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.common.dto.RabbitMessageDto;
import liga.medical.medicalmonitoring.core.annotation.DbLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQListenerImpl implements RabbitMQListener {

    @RabbitListener(queues = "error_queue")
    @DbLog
    public void error(String str) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RabbitMessageDto message = objectMapper.readValue(str, RabbitMessageDto.class);
            System.out.println("ERROR: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
