package eu.jev.springjms.sender;

import eu.jev.springjms.config.JmsConfig;
import eu.jev.springjms.model.ExampleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExampleSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("Sending message");

        ExampleMessage exampleMessage = ExampleMessage.builder()
                .id(UUID.randomUUID())
                .message("Example Message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.QUEUE_NAME, exampleMessage);

        System.out.println("Message Sent!");
    }
}
