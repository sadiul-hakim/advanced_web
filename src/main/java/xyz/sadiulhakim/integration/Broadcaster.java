package xyz.sadiulhakim.integration;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class Broadcaster {

    private final IntegrationGateway integrationGateway;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");

    public Broadcaster(IntegrationGateway integrationGateway) {
        this.integrationGateway = integrationGateway;
    }

//    @Scheduled(fixedRate = 1_000)
//    public void broadcastTime() {
//        String time = FORMATTER.format(ZonedDateTime.now());
//        integrationGateway.broadcast(time);
//    }
}
