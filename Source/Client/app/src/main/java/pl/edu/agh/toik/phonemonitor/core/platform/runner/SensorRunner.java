package pl.edu.agh.toik.phonemonitor.core.platform.runner;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.SensorReading;

/**
 * Created by Imiolak.
 * 24.05.2016
 */
public class SensorRunner {

    private final ISensor sensor;
    private final IOutput[] outputs;

    public SensorRunner(ISensor sensor, IOutput[] outputs) {
        this.sensor = sensor;
        this.outputs = outputs;
    }

    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Iterable<SensorReading> readings = sensor.getCurrentReadings();

                for (SensorReading reading: readings) {
                    String tag = reading.getTag();
                    String message = reading.getReading();

                    if (message == null || message.isEmpty())
                        continue;

                    for (IOutput output: outputs)
                        output.write(tag, message);
                }
            }
        }, TimeUnit.SECONDS.toMillis(0), sensor.getDefaultInterval());
    }
}
