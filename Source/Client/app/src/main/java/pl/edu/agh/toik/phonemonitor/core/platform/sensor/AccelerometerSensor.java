package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

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
public class AccelerometerSensor implements ISensor, SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor sensor;

    private String[] lastReadings;
    private float timestamp;

    public AccelerometerSensor() {
        sensorManager = (SensorManager) Config.context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        lastReadings = new String[3];
    }


    @Override
    public String getSensorName() {
        return "ACC";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("x", lastReadings[0]));
        readings.add(new SensorReading("y", lastReadings[1]));
        readings.add(new SensorReading("z", lastReadings[2]));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastReadings[0] = Float.toString(event.values[0]);
        lastReadings[1] = Float.toString(event.values[1]);
        lastReadings[2] = Float.toString(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
