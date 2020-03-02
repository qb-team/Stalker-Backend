package it.qbteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.qbteam.repository.sql")
@EnableRedisRepositories(basePackages = "it.qbteam.repository.nosql")
public class StalkerBackend {

  public static void main(String[] args) {
    SpringApplication.run(StalkerBackend.class, args);
  }

}