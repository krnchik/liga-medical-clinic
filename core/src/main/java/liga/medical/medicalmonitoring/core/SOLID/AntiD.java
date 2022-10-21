package liga.medical.medicalmonitoring.core.SOLID;

import liga.medical.medicalmonitoring.core.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AntiD {
    private final CommonQueueRabbitMQHandler rabbitMQ;

    @Autowired
    public AntiD(CommonQueueRabbitMQHandler rabbitMQ) {
        this.rabbitMQ = rabbitMQ;
    }


    @PostMapping("/message")
    private ResponseEntity<Message> getMessage(@RequestBody Message message) {
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rabbitMQ.handle(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
