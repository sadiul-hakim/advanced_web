package xyz.sadiulhakim.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class UserRateLimit {
    private int limitPerSecond;
    private int limitPerMinute;
    private Duration timeout;
}