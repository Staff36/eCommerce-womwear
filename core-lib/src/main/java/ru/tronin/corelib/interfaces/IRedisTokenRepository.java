package ru.tronin.corelib.interfaces;

import org.springframework.stereotype.Repository;
import java.time.Duration;



public interface IRedisTokenRepository {
    boolean put(String key, Duration expiration);
    String get(String key);
    boolean hasKey(String key);
}
