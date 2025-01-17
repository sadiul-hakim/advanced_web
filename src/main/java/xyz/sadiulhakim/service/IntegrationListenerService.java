package xyz.sadiulhakim.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class IntegrationListenerService {

    @ServiceActivator(inputChannel = "directChannel")
    public void directChannelMessage(String message) {
        System.out.println(message);
    }
}
