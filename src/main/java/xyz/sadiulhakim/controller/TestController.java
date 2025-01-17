package xyz.sadiulhakim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.sadiulhakim.UserRateLimit;
import xyz.sadiulhakim.service.RateLimiterService;

import java.time.Duration;
import java.util.Map;

@RestController
public class TestController {

    private final RateLimiterService rateLimiterService;

    private static final Map<String, UserRateLimit> rateLimits = Map.of(
            "100", new UserRateLimit(10, 25, Duration.ofSeconds(20)),
            "200", new UserRateLimit(200, 1000, Duration.ofHours(1))
    );

    public TestController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/ping")
    public String pong(@RequestParam String userId) {

        UserRateLimit userLimit = rateLimits.getOrDefault(userId,
                new UserRateLimit(1, 60, Duration.ofSeconds(1))); // Default limit if user not found

        try {
            if (rateLimiterService.acquirePermission(userId, userLimit)) {

                return "pong";
            }

            return "Limit Exceeded";
        } catch (RateLimiterService.RateLimitExceededException e) {
            return "Rate limit exceeded";
        }
    }
}
