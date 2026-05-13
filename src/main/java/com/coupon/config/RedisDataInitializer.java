package com.coupon.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisDataInitializer implements ApplicationRunner {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        stringRedisTemplate.getConnectionFactory()
            .getConnection()
            .serverCommands()
            .flushDb();
    }
}
