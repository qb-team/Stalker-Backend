package it.qbteam.areautils;

import java.util.List;

public class GpsCoordinateInsideGpsPolygon implements PointInsidePolygon {

    /**
     * Implementation of the algorithm explained here:
     * http://www.eecs.umich.edu/courses/eecs380/HANDOUTS/PROJ2/InsidePoly.html
     * In particular, Solution 2 (2D) is implemented.
     *
     * @param polygon
     * @param point
     * @return
     */
    @Override
    public Boolean isPointInsidePolygon(List<Coordinate> polygon, Coordinate point) {
        double angle = 0;
        int n = polygon.size();

        for(int i = 0; i < n; i++) {
            Coordinate currentVertex = polygon.get(i);
            Coordinate nextVertex = polygon.get((i+1) % n);

            double dx1 = currentVertex.getX() - point.getX();
            double dy1 = currentVertex.getY() - point.getY();
            double dx2 = nextVertex.getX() - point.getX();
            double dy2 = nextVertex.getY() - point.getY();

            angle += angle2D(dx1, dy1, dx2, dy2);
        }

        return Math.abs(angle) >= Math.PI;
    }

    private Double angle2D(Double dx1, Double dy1, Double dx2, Double dy2) {
        double theta1 = Math.atan2(dy1, dx1);
        double theta2 = Math.atan2(dy2, dx2);
        double dtheta = theta2 - theta1;

        while (dtheta > Math.PI)
            dtheta -= Math.PI * 2;
        while (dtheta < -Math.PI)
            dtheta += Math.PI * 2;

        return dtheta;
    }
}
