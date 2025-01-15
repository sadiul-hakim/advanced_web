package xyz.sadiulhakim.service;


import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiterService {

    private final RateLimiterRegistry rateLimiterRegistry;

    public RateLimiterService(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    public boolean canLoad(String userId, int limit, Duration timeout) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(limit)
                .limitRefreshPeriod(timeout) // Refresh period should match the timeout
                .timeoutDuration(timeout)
                .build();

        try {
            rateLimiterRegistry.rateLimiter(userId, config).acquirePermission();
            return true;
        } catch (RequestNotPermitted exception) {
            return false;
        }
    }
}
