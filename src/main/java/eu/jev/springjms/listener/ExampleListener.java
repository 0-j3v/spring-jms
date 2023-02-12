package eu.jev.springjms.listener;

import eu.jev.springjms.config.JmsConfig;
import eu.jev.springjms.model.ExampleMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class ExampleListener {

    @JmsListener(destination = JmsConfig.QUEUE_NAME)
    public void listen(@Payload ExampleMessage exampleMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
        System.out.println("Example Message Received");
        System.out.println(exampleMessage);

    }
}
