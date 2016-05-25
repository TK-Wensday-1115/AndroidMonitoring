package pl.edu.agh.toik.phonemonitor.core.platform.output;

import android.util.Log;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class LogCatOutput implements IOutput {

    private final String tag;

    public LogCatOutput(String tag) {
        this.tag = tag;
    }

    @Override
    public void write(String message) {
        Log.d(tag, message);
    }
}
