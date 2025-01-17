package xyz.sadiulhakim.service;


import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.stereotype.Component;
import xyz.sadiulhakim.UserRateLimit;

import java.time.Duration;

@Component
public class RateLimiterService {

    private final RateLimiterRegistry rateLimiterRegistry;

    public RateLimiterService(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    public boolean acquirePermission(String userId, UserRateLimit userLimit) throws RateLimitExceededException {

        // Create rate limiters for both per-second and per-minute limits
        RateLimiterConfig configPerSecond = RateLimiterConfig.custom()
                .limitForPeriod(userLimit.getLimitPerSecond())
                .limitRefreshPeriod(userLimit.getTimeout())
                .timeoutDuration(Duration.ofMillis(1))
                .build();

        RateLimiterConfig configPerMinute = RateLimiterConfig.custom()
                .limitForPeriod(userLimit.getLimitPerMinute())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofMillis(1))
                .build();

        try {
            // Acquire both permits simultaneously
            boolean a = rateLimiterRegistry.rateLimiter(userId + "_per_second", configPerSecond).acquirePermission();
            boolean b = rateLimiterRegistry.rateLimiter(userId + "_per_minute", configPerMinute).acquirePermission();
            return a && b;
        } catch (RequestNotPermitted exception) {
            return false;
        }
    }

    public static class RateLimitExceededException extends RuntimeException {
        public RateLimitExceededException(String message) {
            super(message);
        }
    }
}
