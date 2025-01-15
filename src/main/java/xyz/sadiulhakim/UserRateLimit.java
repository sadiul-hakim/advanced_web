package xyz.sadiulhakim;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class UserRateLimit {
    private int limit;
    private Duration timeout;
}