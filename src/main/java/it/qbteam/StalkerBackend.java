package it.qbteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.qbteam.persistence.repository")
public class StalkerBackend {
  public static void main(String[] args) {
    SpringApplication.run(StalkerBackend.class, args);
  }
}
