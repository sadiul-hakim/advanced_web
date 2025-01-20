package xyz.sadiulhakim.integration.producer;

import org.springframework.stereotype.Component;
import xyz.sadiulhakim.integration.gateway.IntegrationGateway;

import java.time.format.DateTimeFormatter;

@Component
class Broadcaster {

    private final IntegrationGateway integrationGateway;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");

    Broadcaster(IntegrationGateway integrationGateway) {
        this.integrationGateway = integrationGateway;
    }

//    @Scheduled(fixedRate = 1_000)
//    public void broadcastTime() {
//        String time = FORMATTER.format(ZonedDateTime.now());
//        integrationGateway.broadcast(time);
//    }
}
