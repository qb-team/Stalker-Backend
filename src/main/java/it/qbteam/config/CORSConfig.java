package it.qbteam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
    @Value("${LOCAL_URL}")
    private static String localOrigin;
  
    @Value("${REMOTE_URL}")
    private static String remoteOrigin;
    
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