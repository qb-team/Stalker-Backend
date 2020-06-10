package it.qbteam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.qbteam.persistence.repository")
public class StalkerBackend {
  @Value("${LOCAL_URL}")
  private static String localOrigin;

  @Value("${REMOTE_URL}")
  private static String remoteOrigin;

  public static void main(String[] args) {
    SpringApplication.run(StalkerBackend.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins(localOrigin, remoteOrigin);
      }
    };
  }
}
