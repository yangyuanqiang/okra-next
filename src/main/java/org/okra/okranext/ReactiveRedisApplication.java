package com.example.reactiveredis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@SpringBootApplication
public class ReactiveRedisApplication implements ApplicationRunner {
    @Resource
    private ReactiveStringRedisTemplate reactiveRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ReactiveRedisApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ReactiveHashOperations<String, String, String> hashOps = reactiveRedisTemplate.opsForHash();
        Mono mono1 = hashOps.put("apple", "x", "6000");
        mono1.subscribe(System.out::println);
        Mono mono2 = hashOps.put("apple", "xr", "5000");
        mono2.subscribe(System.out::println);
        Mono mono3 = hashOps.put("apple", "xs max", "8000");
        mono3.subscribe(System.out::println);
    }

    @Bean
    ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }
}
