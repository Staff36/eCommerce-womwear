package ru.tronin.corelib.repositories;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import ru.tronin.corelib.interfaces.IRedisTokenRepository;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisTokenRepository implements IRedisTokenRepository {

    @Autowired
    StringRedisTemplate template;

    ValueOperations<String, String> valueOperations;

    @PostConstruct
    public void init(){
        valueOperations = template.opsForValue();
    }

    @Override
    public boolean put(String key, Duration expiration) {
        return valueOperations.setIfAbsent(key, "token", expiration);
    }

    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return template.hasKey(key);
    }
}
