package xyz.sadiulhakim.event.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.sadiulhakim.event.CustomEvent;

@Slf4j
@Component
class CustomConsumer {

    @ApplicationModuleListener
    void eventListener1(CustomEvent event) {
        System.out.println(event.getMsg());
    }

    @Async("customAsyncExecutor")
    @EventListener
    void serverInitializationEvent(WebServerInitializedEvent event) {
        System.out.println("Server is running on port : " + event.getWebServer().getPort());
    }
}
