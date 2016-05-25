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
            default:
                return null;
        }
    }
}
