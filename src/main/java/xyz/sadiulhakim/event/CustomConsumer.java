package xyz.sadiulhakim.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomConsumer {

    @EventListener(CustomEvent.class)
    public void eventListener(CustomEvent event) {
        log.info(event.getMsg());
    }
}
