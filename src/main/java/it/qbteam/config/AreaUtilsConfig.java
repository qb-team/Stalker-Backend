package it.qbteam.config;

import it.qbteam.areautils.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AreaUtilsConfig {
    @Bean
    public GpsAreaFacade gpsAreaFacade() {
        return new GpsAreaFacade(new GpsAreaCalculator(), new GpsCoordinateInsideGpsPolygon(), new GpsCoordinateFactory());
    }
}
