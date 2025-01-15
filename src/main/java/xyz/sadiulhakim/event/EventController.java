package xyz.sadiulhakim.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/event")
public class EventController {
    private final ApplicationEventPublisher eventPublisher;

    public EventController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createEvent(@RequestParam String name) {
        eventPublisher.publishEvent(new CustomEvent(this, "This message is from " + name));
        log.info("Event published");

        return ResponseEntity.ok("Done");
    }
}
