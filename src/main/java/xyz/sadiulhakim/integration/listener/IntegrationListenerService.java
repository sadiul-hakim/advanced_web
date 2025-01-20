package xyz.sadiulhakim.integration.listener;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
class IntegrationListenerService {

//    @ServiceActivator(inputChannel = "inputChannel")
//    public void directChannelMessage1(Message<String> message) {
//        System.out.println(message.getPayload());
//    }

    @ServiceActivator(inputChannel = "inputChannel")
    void studentToJson(Message<?> message) throws MessagingException {
        System.out.println("Input Channel: " + message.getPayload());
    }

    @ServiceActivator(inputChannel = "outputChannel")
    void studentClass(Message<?> message) throws MessagingException {
        System.out.println("Output Channel: " + message.getPayload());
    }

    @ServiceActivator(inputChannel = "backupChannel")
    void backupChannel(Message<?> message) throws MessagingException {
        System.out.println("Backup Channel: " + message.getPayload());
    }

    @ServiceActivator(inputChannel = "backup2Channel")
    void backup2Channel(Message<?> message) throws MessagingException {
        System.out.println("Backup 2 Channel: " + message.getPayload());
    }

    @ServiceActivator(inputChannel = "defaultChannel")
    void defaultChannel(Message<?> message) throws MessagingException {
        System.out.println("Default Channel: " + message.getPayload());
    }
}
