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
public class GyroscopeSensor implements ISensor, SensorEventListener {

    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];

    private final SensorManager sensorManager;
    private final Sensor sensor;

    private String lastReading;
    private float timestamp;

    public GyroscopeSensor() {
        sensorManager = (SensorManager) Config.context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        lastReading = "";
    }


    @Override
    public String getSensorName() {
        return "GYR";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("", lastReading));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(5);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;

            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            double omegaMagnitude = Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
        }

        timestamp = event.timestamp;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
