package xyz.sadiulhakim.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileProcessingIntegrationConfig {

    private static final String INPUT_DIR = "F:\\Spring Integration\\input";  // Directory to read files from
    private static final String OUTPUT_DIR = "F:\\Spring Integration\\output"; // Directory for processed files

    @Bean
    public MessageChannel fileInputChannel() {
        return MessageChannels.queue().getObject();
    }

    @Bean
    public MessageChannel processedFileChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public IntegrationFlow fileProcessorRow() {
        return IntegrationFlow
                .from(
                        Files.inboundAdapter(new File(INPUT_DIR))
                                .filter(new SimplePatternFileListFilter("*.txt")) // Accept only .txt files
                                .autoCreateDirectory(true),
                        e -> e.poller(p -> p.fixedDelay(10_000))// Poll every 10 second
                ).channel(fileInputChannel()) // Input channel
                // Transformer to convert file content to uppercase
                .<File, String>transform(file -> {
                    try {
                        return new String(java.nio.file.Files.readAllBytes(file.toPath())).toUpperCase();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read file", e);
                    }
                }).channel(processedFileChannel()) // Pass to the next channel
                // Service Activator to process the transformed message
                .handle(processFileService())
                .get();
    }


    @Bean
    @ServiceActivator(inputChannel = "processedFileChannel")
    public MessageHandler processFileService() {
        return message -> {
            var result = (String) message.getPayload();
            System.out.println("Processed Content: " + result);

            // Optionally, save the processed content to a file
            File outputFile = new File(OUTPUT_DIR, "processed_" + System.currentTimeMillis() + ".txt");
            try {
                outputFile.getParentFile().mkdirs(); // Ensure directory exists
                java.nio.file.Files.write(outputFile.toPath(), result.getBytes());
                System.out.println("Saved processed file to: " + outputFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Failed to write processed file", e);
            }
        };
    }
}
