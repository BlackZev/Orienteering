package edu.esiea.orientation.utils;

import android.graphics.Color;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {

    private static List<Polygon> distanceCircles = new ArrayList<>();

    public static float getDistanceInMeters(GeoPoint startPoint, GeoPoint endPoint) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(
                startPoint.getLatitude(), startPoint.getLongitude(),
                endPoint.getLatitude(), endPoint.getLongitude(),
                results);
        return results[0];
    }

    public static void drawMinMaxDistanceCirclesAroundPoint(MapView mapView, GeoPoint point, float minDistance, float maxDistance) {
        eraseMinMaxDistanceCircles(mapView);

        Polygon minCircle = new Polygon();
        minCircle.setPoints(Polygon.pointsAsCircle(point, minDistance));
        minCircle.setStrokeColor(Color.GREEN);
        minCircle.setFillColor(Color.argb(50, 0, 255, 0));
        distanceCircles.add(minCircle);

        Polygon maxCircle = new Polygon();
        maxCircle.setPoints(Polygon.pointsAsCircle(point, maxDistance));
        maxCircle.setStrokeColor(Color.RED);
        maxCircle.setFillColor(Color.argb(50, 255, 0, 0));
        distanceCircles.add(maxCircle);

        mapView.getOverlays().addAll(distanceCircles);
        mapView.invalidate();
    }

    public static void eraseMinMaxDistanceCircles(MapView mapView) {
        for (Polygon circle : distanceCircles) {
            mapView.getOverlays().remove(circle);
        }
        distanceCircles.clear();
        mapView.invalidate();
    }
}
