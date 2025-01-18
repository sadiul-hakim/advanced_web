package xyz.sadiulhakim.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import xyz.sadiulhakim.pojo.Student;

@Service
public class IntegrationListenerService {

//    @ServiceActivator(inputChannel = "inputChannel")
//    public void directChannelMessage1(Message<String> message) {
//        System.out.println(message.getPayload());
//    }

    @ServiceActivator(inputChannel = "inputChannel")
    public void studentToJson(Message<?> message) throws MessagingException {
        System.out.println("Input Channel: " + message.getPayload());
    }

    @ServiceActivator(inputChannel = "outputChannel")
    public void studentClass(Message<?> message) throws MessagingException {
        System.out.println("Output Channel: " + message.getPayload());
    }
}
