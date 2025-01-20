package xyz.sadiulhakim.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.modulith.NamedInterface;
import xyz.sadiulhakim.student.pojo.Student;

@NamedInterface("integration-gateway")
@MessagingGateway
public interface IntegrationGateway {

//    @Gateway(requestChannel = "inputChannel")
//    void send(String message);

    @Gateway(requestChannel = "inputChannel")
    void sendStudent(Student student);

    @Gateway(requestChannel = "inputChannel")
    void sendRawJson(String json);

    @Gateway(requestChannel = "timeBroadcaster")
    void broadcast(String date);
}
