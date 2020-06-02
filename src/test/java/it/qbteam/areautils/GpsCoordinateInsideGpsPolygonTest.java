package it.qbteam.areautils;

import it.qbteam.persistence.areautils.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class GpsCoordinateInsideGpsPolygonTest {
    private List<Coordinate> coordinates;

    private PointInsidePolygon pointInsidePolygon;

    private CoordinateFactory coordinateFactory;

    public GpsCoordinateInsideGpsPolygonTest() {
        coordinates = new ArrayList<>();
        coordinateFactory = new GpsCoordinateFactory();
        pointInsidePolygon = new GpsCoordinateInsideGpsPolygon();
    }

    private void setUpCartesianCoordinates() {
        coordinates.clear();
        // building a square with edges of length 2
        coordinates.add(coordinateFactory.buildCoordinate(1,1));
        coordinates.add(coordinateFactory.buildCoordinate(3,1));
        coordinates.add(coordinateFactory.buildCoordinate(3,3));
        coordinates.add(coordinateFactory.buildCoordinate(1,3));
    }

    private void setUpGpsCoordinates() {
        coordinates.clear();
        // building perimeter of Torre Archimede (Padova)
        coordinates.add(coordinateFactory.buildCoordinate(45.411222, 11.887317));
        coordinates.add(coordinateFactory.buildCoordinate(45.411555, 11.887474));
        coordinates.add(coordinateFactory.buildCoordinate(45.411440, 11.887946));
        coordinates.add(coordinateFactory.buildCoordinate(45.411109, 11.887786));
    }

    @Test
    public void testPointIsOutside() {
        setUpCartesianCoordinates();
        boolean isInside = pointInsidePolygon.isPointInsidePolygon(coordinates, coordinateFactory.buildCoordinate(2,4));
        Assert.assertEquals(isInside, false);
    }

    @Test
    public void testPointIsInside() {
        setUpCartesianCoordinates();
        boolean isInside = pointInsidePolygon.isPointInsidePolygon(coordinates, coordinateFactory.buildCoordinate(2,2));
        Assert.assertEquals(isInside, true);
    }

    @Test
    public void testGpsCoordinateIsOutside() {
        setUpGpsCoordinates();
        boolean isInside = pointInsidePolygon.isPointInsidePolygon(coordinates, coordinateFactory.buildCoordinate(45.411237, 11.888027));
        Assert.assertEquals(isInside, false);
    }

    @Test
    public void testGpsCoordinateIsInside() {
        setUpGpsCoordinates();
        boolean isInside = pointInsidePolygon.isPointInsidePolygon(coordinates, coordinateFactory.buildCoordinate(45.411314, 11.887630));
        Assert.assertEquals(isInside, true);
    }
}
