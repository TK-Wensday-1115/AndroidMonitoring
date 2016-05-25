package pl.edu.agh.toik.phonemonitor.core.platform.runner;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;

/**
 * Created by Imiolak.
 * 24.05.2016
 */
public class SensorRunner {

    private final ISensor sensor;
    private final IOutput output;

    public SensorRunner(ISensor sensor, IOutput output) {
        this.sensor = sensor;
        this.output = output;
    }

    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                output.write(sensor.getCurrentReading());
            }
        }, TimeUnit.SECONDS.toMillis(0), sensor.getDefaultInterval());
    }
}
