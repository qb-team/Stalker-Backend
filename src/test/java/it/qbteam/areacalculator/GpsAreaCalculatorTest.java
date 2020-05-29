package it.qbteam.areacalculator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.areacalculator.AreaCalculator.Coordinate;

@RunWith(SpringRunner.class)
public class GpsAreaCalculatorTest {
    private List<Coordinate> coordinates;

    private GpsAreaCalculator areaCalculator;

    public GpsAreaCalculatorTest() {
        areaCalculator = new GpsAreaCalculator();
    }

    @Before
    public void setUp() {
        coordinates = new ArrayList<Coordinate>();
    }

    private void setUpCoordinatesTorreArchimedePadova() {
        coordinates.add(areaCalculator.buildCoordinate(45.411222, 11.887317));
        coordinates.add(areaCalculator.buildCoordinate(45.411555, 11.887474));
        coordinates.add(areaCalculator.buildCoordinate(45.411440, 11.887946));
        coordinates.add(areaCalculator.buildCoordinate(45.411109, 11.887786));
    }

    private void setUpCoordinatesFieraVerona() {
        coordinates.add(areaCalculator.buildCoordinate(45.419270, 10.981346));
        coordinates.add(areaCalculator.buildCoordinate(45.420490, 10.977462));
        coordinates.add(areaCalculator.buildCoordinate(45.418784, 10.976043));
        coordinates.add(areaCalculator.buildCoordinate(45.418969, 10.975177));
        coordinates.add(areaCalculator.buildCoordinate(45.418690, 10.974984));
        coordinates.add(areaCalculator.buildCoordinate(45.418792, 10.974474));
        coordinates.add(areaCalculator.buildCoordinate(45.417949, 10.974078));
        coordinates.add(areaCalculator.buildCoordinate(45.417761, 10.974858));
        coordinates.add(areaCalculator.buildCoordinate(45.415961, 10.973989));
        coordinates.add(areaCalculator.buildCoordinate(45.415065, 10.977551));
        coordinates.add(areaCalculator.buildCoordinate(45.416863, 10.978508));
        coordinates.add(areaCalculator.buildCoordinate(45.416562, 10.979967));
    }

    private void setUpUnsufficientCoordinates() {
        coordinates.add(areaCalculator.buildCoordinate(45.411222, 11.887317));
        coordinates.add(areaCalculator.buildCoordinate(45.411555, 11.887474));
    }

    @Test
    public void testAreaIsCalculatedCorrectly() {
        this.setUpCoordinatesTorreArchimedePadova();

        double area = areaCalculator.calculateArea(coordinates);

        Assert.assertEquals(1513.4, area, 5.0D);

        coordinates.clear();

        this.setUpCoordinatesFieraVerona();

        area = areaCalculator.calculateArea(coordinates);

        // Assert.assertEquals(expected, area, 5.0D);
    }

    @Test
    public void testAreaIs0BecauseCoordinatesAreInsufficient() {
        this.setUpUnsufficientCoordinates();

        double area = areaCalculator.calculateArea(coordinates);

        Assert.assertEquals(0.0D, area, 0.0D);
    }
}