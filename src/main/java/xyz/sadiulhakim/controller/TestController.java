package xyz.sadiulhakim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.sadiulhakim.UserRateLimit;
import xyz.sadiulhakim.service.RateLimiterService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private final RateLimiterService rateLimiterService;

    private static final Map<String, UserRateLimit> rateLimits = new HashMap<>();

    static {
        rateLimits.put("100", new UserRateLimit(10, Duration.ofSeconds(40)));
        rateLimits.put( "200", new UserRateLimit(200, Duration.ofHours(1)));
    }

    public TestController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/ping")
    public String pong(@RequestParam String userId) {

        UserRateLimit userLimit = rateLimits.getOrDefault(userId,
                new UserRateLimit(1, Duration.ofSeconds(1))); // Default limit if user not found

        if (!rateLimiterService.canLoad(userId, userLimit.getLimit(), userLimit.getTimeout())) {
            return "Rate limit exceeded";
        }

        return "pong";
    }
}
