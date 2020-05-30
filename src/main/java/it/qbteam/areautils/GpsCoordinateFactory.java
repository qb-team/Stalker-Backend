package it.qbteam.areautils;

public class GpsCoordinateFactory implements CoordinateFactory {
    @Override
    public Coordinate buildCoordinate(double y, double x) {
        Coordinate c = new GpsCoordinate();

        // setting latitude
        c.setY(y);

        // setting longitude
        c.setX(x);

        return c;
    }
}
