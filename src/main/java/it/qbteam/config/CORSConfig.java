package it.qbteam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
    @Value("${LOCAL_URL}")
    private String localOrigin;
  
    @Value("${REMOTE_URL}")
    private String remoteOrigin;

    @Value("${REMOTE_URL2}")
    private String remoteOrigin2;
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
      System.out.println("LOCAL " + localOrigin + " REMOTE " + remoteOrigin);
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**").allowedMethods("*").allowedOrigins(localOrigin, remoteOrigin, remoteOrigin2);
        }
      };
    }
}