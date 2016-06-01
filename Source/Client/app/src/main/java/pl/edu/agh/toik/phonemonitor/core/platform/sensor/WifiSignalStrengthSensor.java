package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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
public class WifiSignalStrengthSensor implements ISensor {

    @Override
    public String getSensorName() {
        return "WIFI";
    }

    @Override
    public Iterable<SensorReading> getCurrentReadings() {
        WifiManager wifiManager = (WifiManager) Config.context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        int strength = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 10);

        List<SensorReading> readings = new ArrayList<>();
        readings.add(new SensorReading("", Integer.toString(strength)));

        return readings;
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(5);
    }
}
