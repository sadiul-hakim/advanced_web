package xyz.sadiulhakim.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import xyz.sadiulhakim.pojo.Student;

@MessagingGateway
public interface IntegrationGateway {

//    @Gateway(requestChannel = "inputChannel")
//    void send(String message);

    @Gateway(requestChannel = "inputChannel")
    void sendStudent(Student student);

    @Gateway(requestChannel = "inputChannel")
    void sendRawJson(String json);
}
