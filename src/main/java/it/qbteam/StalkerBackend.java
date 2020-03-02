package it.qbteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class StalkerBackend {

  public static void main(String[] args) {
    SpringApplication.run(StalkerBackend.class, args);

    /*
    RedisClient redisClient = RedisClient.create("redis://localhost:63790/0");
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> syncCommands = connection.sync();

    syncCommands.set("key", "Hello, Redis!");

    System.out.println("Connected to Redis");

    connection.close();
    redisClient.shutdown(); */
  }

}