package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class SensorFactory {

    private SensorFactory() { }

    public static ISensor create(String parameter) {
        switch (parameter) {
            case "CPU Usage":
                return new CpuUsageSensor();
            case "Memory Usage":
                return new MemoryUsageSensor();
            case "WiFi Signal Strength":
                return new WifiSignalStrengthSensor();
            case "Battery Level":
                return new BatteryLevelSensor();
            case "GPS Location":
                return new GPSLocationSensor();
            case "Ambient Temperature":
                return new AmbientTemperatureSensor();
            case "Accelerometer":
                return new AccelerometerSensor();
            default:
                return null;
        }
    }
}
