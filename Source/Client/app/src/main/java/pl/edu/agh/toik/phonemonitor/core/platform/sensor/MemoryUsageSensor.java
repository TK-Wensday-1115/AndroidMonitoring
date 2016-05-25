package pl.edu.agh.toik.phonemonitor.core.platform.sensor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import java.util.concurrent.TimeUnit;

import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.gui.Config;

/**
 * Created by Imiolak.
 * 25.05.2016
 */
public class MemoryUsageSensor implements ISensor {

    @Override
    public String getDescription() {
        return "MEM";
    }

    @Override
    public String getCurrentReading() {
        ActivityManager.MemoryInfo mf = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) Config.context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(mf);

        long availableMB = mf.availMem / 1048576L;
        long totalMB = mf.totalMem / 1048576L;

        return String.format("%d %d", availableMB, totalMB);
    }

    @Override
    public long getDefaultInterval() {
        return TimeUnit.SECONDS.toMillis(2);
    }
}
