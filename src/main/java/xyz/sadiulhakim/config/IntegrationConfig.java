package xyz.sadiulhakim.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;
import xyz.sadiulhakim.student.pojo.Student;

@Configuration
//@EnableIntegration
//@IntegrationComponentScan
class IntegrationConfig {

    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel backupChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel backup2Channel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel defaultChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel timeBroadcaster() {
        return new PublishSubscribeChannel();
    }

    @Bean
    ObjectToJsonTransformer objectToJsonTransformer() {
        return new ObjectToJsonTransformer(mapper());
    }

    @Bean
    JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(Student.class);
    }

    @Bean
    Jackson2JsonObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonObjectMapper(mapper);
    }

    @Bean
    IntegrationFlow integrationFlow() {
        return IntegrationFlow.from("inputChannel")
                .log(LoggingHandler.Level.INFO, "Message received in inputChannel") // Log message entry
                .enrichHeaders(headers -> {
                    headers.header("source", "inputChannel")  // Add a custom header
                            .header("processedBy", "integrationFlow")  // Add another custom header
                            .headerFunction("customTimestamp", m -> System.currentTimeMillis());  // Add dynamic header
                })
                .transform(objectToJsonTransformer())
                .log(LoggingHandler.Level.INFO, "Message transformed to JSON")
                .filter(Student.class, s -> s.name().startsWith("Sadi"))
                .channel("outputChannel")
                .log(LoggingHandler.Level.INFO, "Message passed to outputChannel")
                .transform(jsonToObjectTransformer())
                .log(LoggingHandler.Level.INFO, "Message transformed back to Student")
                .filter(nameContainsHakim())
                .log(LoggingHandler.Level.INFO, "Student is filter by name")
//                .route(payloadTypeRouter())
                .route(recipientListRouter())
                .get();
    }

    @Bean
    RecipientListRouter recipientListRouter() {
        RecipientListRouter listRouter = new RecipientListRouter();
        listRouter.addRecipient("backupChannel");
        listRouter.addRecipient("backup2Channel");
        listRouter.setDefaultOutputChannel(defaultChannel());

        return listRouter;
    }

    @Bean
    PayloadTypeRouter payloadTypeRouter() {

        PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(Student.class.getName(), "backupChannel");
        router.setDefaultOutputChannel(defaultChannel());
        return router;
    }

    @Bean
    GenericSelector<Student> nameContainsHakim() {
        return student -> student.name().contains("Hakim");
    }
}
