package xyz.sadiulhakim.integration;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
class BroadcastListener {

    @ServiceActivator(inputChannel = "timeBroadcaster")
    void listener1(String date) {
        System.out.println(date + " from listener 1");
    }

    @ServiceActivator(inputChannel = "timeBroadcaster")
    void listener2(String date) {
        System.out.println(date + " from listener 2");
    }
}
