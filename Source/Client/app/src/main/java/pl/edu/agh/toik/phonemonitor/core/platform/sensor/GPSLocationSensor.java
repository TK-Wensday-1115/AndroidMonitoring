package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.SensorReading;
import pl.edu.agh.toik.phonemonitor.gui.Config;

/**
 * Created by Imiolak.
 * 31.05.2016
 */
public class GPSLocationSensor implements ISensor {
    @Override
    public String getSensorName() {
        return "GPS";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        LocationManager locationManager = (LocationManager) Config.context.getSystemService(Context.LOCATION_SERVICE);
        List<SensorReading> readings = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(Config.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Config.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            readings.add(new SensorReading("", ""));
            return readings;
        }

        Location netLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (netLocation != null) {
            readings.add(new SensorReading("", stringifyLocation(netLocation)));
            return readings;
        }

        Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            readings.add(new SensorReading("", stringifyLocation(gpsLocation)));
            return readings;
        }

        readings.add(new SensorReading("", ""));
        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(15);
    }

    private String stringifyLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        return Double.toString(latitude) + " " + longitude + " " + altitude;
    }
}
