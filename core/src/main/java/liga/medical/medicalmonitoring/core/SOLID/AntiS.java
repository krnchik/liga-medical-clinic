package liga.medical.medicalmonitoring.core.SOLID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import liga.medical.medicalmonitoring.core.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AntiS {

    private final AmqpTemplate template;

    @Autowired
    public AntiS(AmqpTemplate template) {
        this.template = template;
    }


    @PostMapping("/message")
    private ResponseEntity<Message> getMessage(@RequestBody Message message) {
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(message);
            template.convertAndSend("common_monitoring", message.getStatus().toString(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
