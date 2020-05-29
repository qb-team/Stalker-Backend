package it.qbteam.areacalculator;

import java.util.List;

public interface AreaCalculator {
    public interface Coordinate {
        void setY(double y);
        void setX(double x);
        Double getY();
        Double getX();
    }

    Coordinate buildCoordinate(double y, double x);

    Double calculateArea(List<Coordinate> coordinates);
}