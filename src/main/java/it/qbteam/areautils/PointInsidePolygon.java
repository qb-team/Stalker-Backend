package it.qbteam.areautils;

import java.util.List;

public interface PointInsidePolygon {
    Boolean isPointInsidePolygon(List<Coordinate> polygon, Coordinate point);
}
