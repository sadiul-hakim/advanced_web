package xyz.sadiulhakim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.sadiulhakim.integration.IntegrationGateway;
import xyz.sadiulhakim.pojo.Student;

@RestController
public class IntegrationController {

    private final IntegrationGateway gateway;

    public IntegrationController(IntegrationGateway gateway) {
        this.gateway = gateway;
    }


    @GetMapping("/integrate/{text}")
    public ResponseEntity<?> integrate(@PathVariable String text) {
//        Message<String> message = MessageBuilder.withPayload(text)
//                .setHeader("name", "Hakim")
//                .build();
//        gateway.send(text);
        return ResponseEntity.ok("Done");
    }

    @PostMapping("/send-student")
    public ResponseEntity<?> sendStudent(@RequestBody Student st){
        gateway.sendStudent(st);
        return ResponseEntity.ok("Done");
    }
}
