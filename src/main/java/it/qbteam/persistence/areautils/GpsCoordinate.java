package it.qbteam.persistence.areautils;

public class GpsCoordinate implements Coordinate {
    private double longitude; // vertical lines, go left (east) and right (west) the X axis
    private double latitude; // horizontal lines, go up (north) and down (south) the Y axis
    
    @Override
    public void setX(Double x) {
        this.longitude = x;
    }

    @Override
    public void setY(Double y) {
        this.latitude = y;
    }

    @Override
    public Double getX() {
        return longitude;
    }

    @Override
    public Double getY() {
        return latitude;
    }
}
