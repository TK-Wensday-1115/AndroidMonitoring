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
public class AmbientTemperatureSensor implements ISensor, SensorEventListener {

    private String lastReading;

    public AmbientTemperatureSensor() {
        SensorManager sensorManager = (SensorManager) Config.context.getSystemService(Context.SENSOR_SERVICE);
        Sensor tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);

        lastReading = "";
    }

    @Override
    public String getSensorName() {
        return "TEMP";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("", lastReading));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(10);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float reading = event.values[0];
        lastReading = Float.toString(reading);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
