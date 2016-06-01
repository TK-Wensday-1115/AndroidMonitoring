package pl.edu.agh.toik.phonemonitor.core.common.sensor;

import android.hardware.SensorEventListener;

/**
 * Created by Imiolak.
 * 31.05.2016
 */
public class SensorReading {
    private final String tag;
    private final String reading;

    public SensorReading(String tag, String reading) {

        this.tag = tag;
        this.reading = reading;
    }

    public String getTag() {
        return tag;
    }

    public String getReading() {
        return reading;
    }
}
