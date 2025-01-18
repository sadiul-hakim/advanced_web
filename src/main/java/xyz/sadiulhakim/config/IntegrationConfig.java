package xyz.sadiulhakim.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;
import xyz.sadiulhakim.pojo.Student;

@Configuration
//@EnableIntegration
//@IntegrationComponentScan
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel backupChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel defaultChannel() {
        return new DirectChannel();
    }

    @Bean
    public ObjectToJsonTransformer objectToJsonTransformer() {
        return new ObjectToJsonTransformer(mapper());
    }

    @Bean
    public JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(Student.class);
    }

    @Bean
    public Jackson2JsonObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonObjectMapper(mapper);
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlow.from("inputChannel")
                .log(LoggingHandler.Level.INFO, "Message received in inputChannel") // Log message entry
                .enrichHeaders(headers -> {
                    headers.header("source", "inputChannel")  // Add a custom header
                            .header("processedBy", "integrationFlow")  // Add another custom header
                            .headerFunction("customTimestamp", m -> System.currentTimeMillis());  // Add dynamic header
                })
                .transform(objectToJsonTransformer())
                .log(LoggingHandler.Level.INFO, "Message transformed to JSON")
                .channel("outputChannel")
                .log(LoggingHandler.Level.INFO, "Message passed to outputChannel")
                .transform(jsonToObjectTransformer())
                .log(LoggingHandler.Level.INFO, "Message transformed back to Student")
                .route(payloadTypeRouter())
                .get();
    }

    @Bean
    public PayloadTypeRouter payloadTypeRouter() {
        PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(Student.class.getName(), "backupChannel");
        router.setDefaultOutputChannel(defaultChannel());
        return router;
    }

    @Bean
    public IntegrationFlow backupFlow() {
        return IntegrationFlow.from("inputChannel")
                .log("Message routed to backupChannel")
                .handle((p, h) -> {
                    // Additional logic can be placed here if needed
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow defaultFlow() {
        return IntegrationFlow.from("defaultChannel")
                .log("Message routed to defaultChannel")
                .handle((p, h) -> {
                    // Additional logic can be placed here if needed
                    return null;
                })
                .get();
    }
}
