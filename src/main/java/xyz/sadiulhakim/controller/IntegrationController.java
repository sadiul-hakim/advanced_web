package xyz.sadiulhakim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.sadiulhakim.integration.gateway.IntegrationGateway;
import xyz.sadiulhakim.student.pojo.Student;

@RestController
class IntegrationController {

    private final IntegrationGateway gateway;

    IntegrationController(IntegrationGateway gateway) {
        this.gateway = gateway;
    }

    @GetMapping("/integrate/{text}")
    ResponseEntity<?> integrate(@PathVariable String text) {
//        Message<String> message = MessageBuilder.withPayload(text)
//                .setHeader("name", "Hakim")
//                .build();
//        gateway.send(text);
        return ResponseEntity.ok("Done");
    }

    @PostMapping("/send-student")
    ResponseEntity<?> sendStudent(@RequestBody Student st){
        gateway.sendStudent(st);
        return ResponseEntity.ok("Done");
    }
}
