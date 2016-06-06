package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.SensorReading;
import pl.edu.agh.toik.phonemonitor.gui.Config;

/**
 * Created by Imiolak.
 * 25.05.2016
 */
public class MemoryUsageSensor implements ISensor {

    @Override
    public String getSensorName() {
        return "MEM";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        ActivityManager.MemoryInfo mf = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) Config.context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(mf);

        long availableMB = mf.availMem / 1048576L;
        long totalMB = mf.totalMem / 1048576L;

        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("1", Long.toString(totalMB - availableMB)));
        readings.add(new SensorReading("2", Long.toString(totalMB)));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(2);
    }
}
