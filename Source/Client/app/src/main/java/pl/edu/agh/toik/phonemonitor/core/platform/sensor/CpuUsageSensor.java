package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class CpuUsageSensor implements ISensor {

    @Override
    public String getDescription() {
        return "CPU";
    }

    @Override
    public String getCurrentReading() {
        Process p = null;
        BufferedReader reader = null;
        String result = null;

        try {
            p = Runtime.getRuntime().exec("top -n 1");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (result == null || result.contentEquals("")) {
                result = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                p.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] all = result.split("[A-Za-z\\s%,]+");
        return all[1];
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(1);
    }
}
