package it.qbteam.areacalculator;

import java.util.ArrayList;
import java.util.List;

public class GpsAreaCalculator implements AreaCalculator {
    private static final double EARTH_CIRCUMFERENCE = 6371000 * 2 * Math.PI; // approximated radius in meters

    private class GpsCoordinate implements Coordinate {
        private double longitude; // vertical lines, go left (east) and right (west) the X axis
        private double latitude; // horizontal lines, go up (north) and down (south) the Y axis

        public void setX(double x) {
            this.longitude = x;
        }

        public void setY(double y) {
            this.latitude = y;
        }

        public Double getX() {
            return longitude;
        }

        public Double getY() {
            return latitude;
        }
    }

    @Override
    public Coordinate buildCoordinate(double y, double x) {
        Coordinate c = new GpsCoordinate();

        // setting latitude
        c.setY(y);

        // setting longitude
        c.setX(x);

        return c;
    }

    @Override
    public Double calculateArea(List<Coordinate> coordinates) {
        if (coordinates.size() < 3) {
            return 0D;
        }

        final List<Double> listY = new ArrayList<Double>();
        final List<Double> listX = new ArrayList<Double>();
        final List<Double> listArea = new ArrayList<Double>();

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
        double areasSum = listArea.stream().mapToDouble(d -> d).sum();
        // double areasSum = 0;
        // for (final Double area : listArea) {
        //     areasSum = areasSum + area;
        // }

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