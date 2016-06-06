package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.hardware.Sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.SensorReading;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class CpuUsageSensor implements ISensor {

    @Override
    public String getSensorName() {
        return "CPU";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        Process p = null;
        BufferedReader reader = null;
        String result = null;

        try {
            p = Runtime.getRuntime().exec("top");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (result == null || result.contentEquals("")) {
                result = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                p.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] usages = result.split("[A-Za-z\\s%,]+");

        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("User", usages[1]));
        readings.add(new SensorReading("System", usages[2]));
        readings.add(new SensorReading("IOW", usages[3]));
        readings.add(new SensorReading("IRQ", usages[4]));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(3);
    }
}
