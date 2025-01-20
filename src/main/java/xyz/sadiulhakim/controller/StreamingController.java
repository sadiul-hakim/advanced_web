package xyz.sadiulhakim.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
class StreamingController {

    @GetMapping("/stream")
    ResponseBodyEmitter stream() {
        var emitter = new ResponseBodyEmitter();

        Thread.ofVirtual().start(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    emitter.send("Data chunk " + i + "\n");
                    Thread.sleep(100);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @GetMapping("/time")
    SseEmitter streamSse() {
        var sse = new SseEmitter();

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
        var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                sse.send("Time : " + formatter.format(ZonedDateTime.now()));
            } catch (Exception ex) {
                sse.complete();
                executor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);

        return sse;
    }
}
