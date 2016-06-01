package pl.edu.agh.toik.phonemonitor.core.platform.output;

import android.util.Log;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class LogCatOutput implements IOutput {

    private final String sensorName;

    public LogCatOutput(String sensorName) {

        this.sensorName = sensorName;
    }

    @Override
    public void write(String tag, String message) {
        Log.d(sensorName, tag + " " + message);
    }
}
