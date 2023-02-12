package eu.jev.springjms.listener;

import eu.jev.springjms.config.JmsConfig;
import eu.jev.springjms.model.ExampleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExampleListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.QUEUE_NAME)
    public void listen(@Payload ExampleMessage exampleMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {

    }

    @JmsListener(destination = JmsConfig.SND_RCV_QUEUE)
    public void listenForExample(@Payload ExampleMessage exampleMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException {
        ExampleMessage payloadMsg = ExampleMessage.builder()
                .id(UUID.randomUUID())
                .message("Example!!!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);

    }
}
