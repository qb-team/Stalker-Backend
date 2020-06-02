package it.qbteam.persistence.areautils;

import java.util.List;

public class GpsAreaFacade {
    private AreaCalculator areaCalculator;

    private PointInsidePolygon pointInsidePolygon;

    private CoordinateFactory coordinateFactory;

    public GpsAreaFacade(AreaCalculator areaCalculator, PointInsidePolygon pointInsidePolygon, CoordinateFactory coordinateFactory) {
        this.areaCalculator = areaCalculator;
        this.pointInsidePolygon = pointInsidePolygon;
        this.coordinateFactory = coordinateFactory;
    }

    public double calculateArea(List<Coordinate> coordinates) {
        return areaCalculator.calculateArea(coordinates);
    }

    public boolean isPointInsidePolygon(List<Coordinate> coordinates, Coordinate point) {
        return pointInsidePolygon.isPointInsidePolygon(coordinates, point);
    }

    public Coordinate buildCoordinate(double y, double x) {
        return coordinateFactory.buildCoordinate(y, x);
    }
}
