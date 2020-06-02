package it.qbteam.config;

import it.qbteam.persistence.areautils.GpsAreaCalculator;
import it.qbteam.persistence.areautils.GpsAreaFacade;
import it.qbteam.persistence.areautils.GpsCoordinateFactory;
import it.qbteam.persistence.areautils.GpsCoordinateInsideGpsPolygon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AreaUtilsConfig {
    @Bean
    public GpsAreaFacade gpsAreaFacade() {
        return new GpsAreaFacade(new GpsAreaCalculator(), new GpsCoordinateInsideGpsPolygon(), new GpsCoordinateFactory());
    }
}
