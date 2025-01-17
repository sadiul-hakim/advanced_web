package xyz.sadiulhakim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationController {

    private final MessageChannel directChannel;

    public IntegrationController(MessageChannel directChannel) {
        this.directChannel = directChannel;
    }

    @GetMapping("/integrate/{text}")
    public ResponseEntity<?> integrate(@PathVariable String text) {
        Message<String> message = MessageBuilder.withPayload(text)
                .setHeader("name", "Hakim")
                .build();

        directChannel.send(message);

        return ResponseEntity.ok("Done");
    }
}
