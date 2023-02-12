package eu.jev.springjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.jev.springjms.config.JmsConfig;
import eu.jev.springjms.model.ExampleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExampleSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        ExampleMessage exampleMessage = ExampleMessage.builder()
                .id(UUID.randomUUID())
                .message("Example Message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.QUEUE_NAME, exampleMessage);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        ExampleMessage message = ExampleMessage.builder()
                .id(UUID.randomUUID())
                .message("Example Message")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.SND_RCV_QUEUE, session -> {
            try {
                Message exampleMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                exampleMessage.setStringProperty("_type", "eu.jev.springjms.model.ExampleMessage");
                return exampleMessage;
            } catch (JsonProcessingException e) {
                throw new JMSException("Failed");
            }
        });

        System.out.println(receivedMsg.getBody(String.class));
    }
}
