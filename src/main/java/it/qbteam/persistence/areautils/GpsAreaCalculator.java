package it.qbteam.persistence.areautils;

import java.util.ArrayList;
import java.util.List;

public class GpsAreaCalculator implements AreaCalculator {
    private static final double EARTH_CIRCUMFERENCE = 6371000 * 2 * Math.PI; // approximated radius in meters

    @Override
    public Double calculateArea(List<Coordinate> coordinates) {
        if (coordinates.size() < 3) {
            return 0D;
        }

        final List<Double> listY = new ArrayList<>();
        final List<Double> listX = new ArrayList<>();
        final List<Double> listArea = new ArrayList<>();

        // calculate segment x and y in degrees for each point
        final double latitudeRef = coordinates.get(0).getY();
        final double longitudeRef = coordinates.get(0).getX();

        for (int i = 1; i < coordinates.size(); i++) {
            listY.add(calculateYSegment(latitudeRef, coordinates.get(i).getY()));
            listX.add(calculateXSegment(longitudeRef, coordinates.get(i).getX(), coordinates.get(i).getY()));
        }

        // calculate areas for each triangle segment
        for (int i = 1; i < listX.size(); i++) {
            final double x1 = listX.get(i - 1);
            final double y1 = listY.get(i - 1);
            final double x2 = listX.get(i);
            final double y2 = listY.get(i);
            listArea.add(calculateAreaInSquareMeters(x1, x2, y1, y2));
        }

        // sum areas of all triangle segments
        double areasSum = 0;
        for (final Double area : listArea) {
            areasSum = areasSum + area;
        }

        // if area is negative, returns the positive value
        return Math.abs(areasSum);
    }

    private Double calculateAreaInSquareMeters(double x1, double x2, double y1, double y2) {
        return (y1 * x2 - x1 * y2) / 2;
    }

    private Double calculateXSegment(double longitudeRef, double longitude, double latitude) {
        return (longitude - longitudeRef) * EARTH_CIRCUMFERENCE * Math.cos(Math.toRadians(latitude)) / 360.0;
    }
    
    private Double calculateYSegment(double latitudeRef, double latitude) {
        return (latitude - latitudeRef) * EARTH_CIRCUMFERENCE / 360.0;
    }
}
