package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

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
public class BatteryLevelSensor implements ISensor {
    @Override
    public String getSensorName() {
        return "BAT";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = Config.context.registerReceiver(null, filter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryLevel = level / (float)scale * 100;

        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("", Float.toString(batteryLevel)));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.MINUTES.toMillis(1);
    }
}
